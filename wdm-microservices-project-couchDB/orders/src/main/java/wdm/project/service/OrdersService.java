package wdm.project.service;

import java.util.HashSet;
import java.util.List;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.Order;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.repository.OrderRepository;
import wdm.project.service.clients.PaymentsServiceClient;
import wdm.project.service.clients.StocksServiceClient;

@Service
@CacheConfig(cacheNames={"orders"})
public class OrdersService {

    @Autowired
    private StocksServiceClient stocksServiceClient;
    @Autowired
    private PaymentsServiceClient paymentsServiceClient;
    @Autowired
    private OrderRepository ordersRepository;

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

        return order.getId();
    }

    /**
     * Removes an order in the micro-service's database that corresponds
     * to a give unique order id.
     *
     * @param orderId the id of the order to be deleted
     * @throws OrderException when the order with the provided ID is not found
     */
    public void removeOrder(String orderId) throws OrderException {
        if (ordersRepository.contains(orderId)) {
            Order storedOrder = ordersRepository.get(orderId);
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
     *                        order with the provided ID is not found
     */
    public OrdersWrapper findOrder(String orderId) throws OrderException {
        if (orderId == null) {
            throw new OrderException("The order id was not provided");
        }

        Order order;

        try {
            order = ordersRepository.get(orderId);
        } catch (Exception exception) {
            throw new OrderException("The order does not exist");
        }

        OrdersWrapper ordersWrapper = new OrdersWrapper();

        try {
            ordersWrapper.setOrderItems(order.getOrderItems());
            ordersWrapper.setPaymentStatus(paymentsServiceClient.getPaymentStatus(orderId));
            ordersWrapper.setUserId(order.getUserId());
        } catch (FeignException exception) {
            throw new OrderException("Something went wrong while retrieving the payment status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ordersWrapper;
    }

    /**
     * Adds an item to a specified order.
     *
     * @param requestOrderItem contains the amount that of the items
     *                         in the order
     * @param itemId           the id of the item
     * @param orderId          the id of the order the item is linked to
     */
    public void addItem(ItemInfo requestOrderItem, String orderId, String itemId) throws OrderException {

        checkItems(orderId, itemId);

        HashSet itemsCache = new HashSet<>(findAllItems());

        if (!itemsCache.contains(itemId)) {
            throw new OrderException("Item id does not exist: ", HttpStatus.NOT_FOUND);
        }

        Order storedOrder = ordersRepository.get(orderId);
        List<ItemInfo> storedItems = storedOrder.getOrderItems();

        boolean storedFlag = false;

        if (!storedItems.isEmpty()) {
            for (ItemInfo storedItem : storedItems) {
                if (storedItem.getId().equals(itemId)) {
                    storedItem.setAmount(storedItem.getAmount() + requestOrderItem.getAmount());
                    ordersRepository.update(storedOrder);
                    storedFlag = true;
                    break;
                }
            }
        }

        if (!storedFlag) {
            ItemInfo newItem = new ItemInfo();
            newItem.setAmount(requestOrderItem.getAmount());
            newItem.setId(itemId);
            storedItems.add(newItem);
            ordersRepository.update(storedOrder);
        }

    }

    @Cacheable
    public List<String> findAllItems(){
        return stocksServiceClient.getAllItemIds();
    }

    /**
     * Removes a specified item from a specified order.
     *
     * @param orderId the id of the order that the item is removed from
     * @param itemId  the id of the item that is to be removed
     * @throws OrderException when the OrderItem with provided IDs cannot be found.
     */
    public void removeItem(String orderId, String itemId) throws OrderException {

        checkItems(orderId, itemId);

        if (ordersRepository.contains(orderId)) {
            Order storedOrder = ordersRepository.get(orderId);
            List<ItemInfo> storedItems = storedOrder.getOrderItems();

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
     * @return the status of the transaction SUCCESS for a
     * successful transaction
     * FAILURE for a failed transaction.
     */
    public void checkoutOrder(String orderId) throws OrderException {
        OrdersWrapper order = findOrder(orderId);
        Integer price;
        try {
            price = stocksServiceClient.subtractItems(order.getOrderItems());
        } catch (FeignException exception) {
            if (exception.status() == 400) {
                throw new OrderException("Insufficient stock", HttpStatus.BAD_REQUEST);
            } else {
                throw new OrderException("Something went wrong when subtracting the stock.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            paymentsServiceClient.payOrder(orderId, order.getUserId(), price);
        } catch (FeignException exception) {
            if (exception.status() == 400) {
                stocksServiceClient.addItems(order.getOrderItems());
                throw new OrderException("Not enough credit", HttpStatus.BAD_REQUEST);
            } else {
                throw new OrderException("Something went wrong when paying the order.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void checkItems(String orderId, String itemId) throws OrderException {
        if (orderId == null) {
            throw new OrderException("Order id was not provided");
        }
        if (itemId == null) {
            throw new OrderException("Item id was not provided");
        }
        boolean existsOrder = ordersRepository.contains(orderId);
        if (!existsOrder) {
            throw new OrderException("There is no order with it \"" + orderId + "\"");
        }
    }
}
