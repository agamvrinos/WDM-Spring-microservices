package wdm.project.service;

import java.util.List;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.JournalEntry;
import wdm.project.dto.Order;
import wdm.project.dto.OrdersWrapper;
import wdm.project.enums.Event;
import wdm.project.enums.Status;
import wdm.project.exception.OrderException;
import wdm.project.repository.JournalRepository;
import wdm.project.repository.OrderRepository;
import wdm.project.service.clients.PaymentsServiceClient;
import wdm.project.service.clients.StocksServiceClient;

@Service
public class OrdersService {

	@Autowired
	private StocksServiceClient stocksServiceClient;
	@Autowired
	private PaymentsServiceClient paymentsServiceClient;
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private JournalRepository journalRepository;

    /**
     * Initializes an order in the micro-service's database with zero
     * total and a given user id that corresponds to the user this order
     * is linked to. Returns the id of the order that is created.
     *
     * @param userId the id of the user this order is linked to
     * @return the order that is created
     * @throws OrderException in case the id of the user was not provided
     */
    public String createOrder(String userId) throws OrderException {
    	if (userId == null) {
    		throw new OrderException("User id was not provided", HttpStatus.BAD_REQUEST);
	    }
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(0);
        ordersRepository.add(order);

        return  order.getId();
    }

    /**
     * Removes an order in the micro-service's database that corresponds
     * to a give unique order id.
     *
     * @param orderId the id of the order to be deleted
     * @throws OrderException when the order with the provided ID is not found
     */
    public void removeOrder(String orderId) throws OrderException {

        List<Order> orders = ordersRepository.findOrder(orderId);

        if (!orders.isEmpty()) {
            Order storedOrder = orders.get(0);
            ordersRepository.remove(storedOrder);
        } else {
            throw new OrderException("There is no order with ID " + orderId + ".", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds the information of an order in the micro-service's database
     * given an unique order id. Returns the user that this order is linked
     * to, the ids of the items that this order has and the payment status.
     *
     * @param orderId the id of the order
     * @return the order information (user id, items' id, payment status)
     * @throws OrderException when the order id was not provided or the
     * order with the provided ID is not found
     */
    public OrdersWrapper findOrder(String orderId) throws OrderException {
		if (orderId == null) {
			throw new OrderException("The order id was not provided");
		}

        List<Order> orders = ordersRepository.findOrder(orderId);

		if (orders.isEmpty()){
            throw new OrderException("The order does not exist");
        }

        Order order  = orders.get(0);
        OrdersWrapper ordersWrapper = new OrdersWrapper();
        ordersWrapper.setOrderItems(order.getOrderItems());
        ordersWrapper.setPaymentStatus(paymentsServiceClient.getPaymentStatus(orderId));
        ordersWrapper.setUserId(order.getUserId());

        return ordersWrapper;
    }

    /**
     * Adds an item to a specified order.
     *
     * @param requestOrderItem contains the amount that of the items
     * in the order
     * @param itemId the id of the item
     * @param orderId the id of the order the item is linked to
     */
    public void addItem(ItemInfo requestOrderItem, String orderId, String itemId) throws OrderException {

        checkItems(orderId, itemId);

        // Check whether item exists.
        try {
            stocksServiceClient.getItem(itemId);
        } catch (FeignException exception) {
            throw new OrderException("There is no item with id \"" + itemId + "\"");
        }

	    Order storedOrder = ordersRepository.findOrder(orderId).get(0);
	    List<ItemInfo> storedItems =  storedOrder.getOrderItems();

	    boolean storedFlag = false;

        if (!storedItems.isEmpty()){
            for (ItemInfo storedItem : storedItems) {
                if (storedItem.getId().equals(itemId)) {
                    storedItem.setAmount(storedItem.getAmount() + requestOrderItem.getAmount());
                    ordersRepository.update(storedOrder);
                    storedFlag = true;
                    break;
                }
            }
        }

        if(!storedFlag){
            ItemInfo newItem = new ItemInfo();
            newItem.setAmount(requestOrderItem.getAmount());
            newItem.setId(itemId);
            storedItems.add(newItem);
            ordersRepository.update(storedOrder);
        }

    }

    /**
     * Removes a specified item from a specified order.
     *
     * @param orderId the id of the order that the item is removed from
     * @param itemId the id of the item that is to be removed
     * @throws OrderException when the OrderItem with provided IDs cannot be found.
     */
    public void removeItem(String orderId, String itemId) throws OrderException {

        checkItems(orderId, itemId);

        List<Order> orders = ordersRepository.findOrder(orderId);

        if (!orders.isEmpty()){
            Order storedOrder = orders.get(0);
            List<ItemInfo> storedItems =  storedOrder.getOrderItems();

            for (ItemInfo storedItem : storedItems) {
                if (storedItem.getId().equals(itemId)) {
                    storedItems.remove(storedItem);
                    ordersRepository.update(storedOrder);
                    break;
                }
            }
        } else {
            throw new OrderException("Unable to remove item with ID " + itemId + " from order with ID " + orderId + ".", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks-out an order invoking every other micro-service.
     *
     * @param orderId the id of order
     * successful transaction
     * FAILURE for a failed transaction.
     */
    public void  checkoutOrder(String orderId) throws OrderException {
        OrdersWrapper order = findOrder(orderId);
        JournalEntry checkoutEntry;
        String id = orderId + "-" + Event.CHECKOUT;

        List<JournalEntry> journalEntries = journalRepository.findJournal(id);

        if (!journalEntries.isEmpty()) {
            checkoutEntry = journalEntries.get(0);
        } else {
            checkoutEntry = new JournalEntry(id, Status.STOCK_PENDING, -1);
        }
        checkoutOrder(orderId, checkoutEntry, order);
    }

    private void checkoutOrder(String orderId, JournalEntry checkoutEntry, OrdersWrapper order) throws OrderException {
        switch (Status.findStatusEnum(checkoutEntry.getStatus())) {
            case SUCCESS: return;
            case STOCK_FAILURE: throw new OrderException("The stock was not sufficient to check out order with order ID " + orderId, HttpStatus.BAD_REQUEST);
            case PAYMENT_FAILURE: throw new OrderException("The credit of user " + order.getUserId() + " was not sufficient to pay " + checkoutEntry.getPrice(), HttpStatus.BAD_REQUEST);
            case STOCK_PENDING: {
                try {
                    Integer price = stocksServiceClient.subtractItems(orderId, order.getOrderItems());
                    checkoutEntry.setPrice(price);
                    checkoutEntry.setStatus(Status.STOCK_SUCCESS);
                } catch (FeignException exception) {
                    if (exception.status() == 400) {
                        checkoutEntry.setStatus(Status.STOCK_FAILURE);
                    } else {
                        throw new OrderException("Something went wrong when subtracting the item stock.");
                    }
                }
                break;
            }
            case STOCK_SUCCESS: {
                try {
                    paymentsServiceClient.payOrder(orderId, order.getUserId(), checkoutEntry.getPrice());
                    checkoutEntry.setStatus(Status.SUCCESS);
                } catch (FeignException exception) {
                    if (exception.status() == 400) {
                        checkoutEntry.setStatus(Status.ROLLBACK_PENDING);
                    } else {
                        throw new OrderException("Something went wrong when attempting to reduce the credit.");
                    }
                }
                break;
            }
            case ROLLBACK_PENDING: {
                try {
                    stocksServiceClient.addItems(orderId, order.getOrderItems());
                    checkoutEntry.setStatus(Status.PAYMENT_FAILURE);
                } catch (FeignException exception) {
                    throw new OrderException("Something went wrong when attempting the rollback on the stock.");
                }
            }
        }

        // Update already existing checkout entry document
        if (!journalRepository.findJournal(checkoutEntry.getId()).isEmpty()) {
            journalRepository.update(checkoutEntry);
        }
        // If it does not exist, create a new one
        else {
            journalRepository.add(checkoutEntry);
        }
        checkoutOrder(orderId, checkoutEntry, order);
    }

    private void checkItems(String orderId, String itemId)throws OrderException{
        if (orderId == null) {
            throw new OrderException("Order id was not provided");
        }
        if (itemId == null) {
            throw new OrderException("Item id was not provided");
        }
        boolean existsOrder = ordersRepository.findOrder(orderId).isEmpty();
        if (existsOrder) {
            throw new OrderException("There is no order with it \"" + orderId + "\"");
        }
    }
}
