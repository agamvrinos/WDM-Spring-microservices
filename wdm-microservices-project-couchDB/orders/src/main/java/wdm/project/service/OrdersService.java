package wdm.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.repository.OrderItemRepository;
import wdm.project.repository.OrderRepository;
import wdm.project.service.clients.PaymentsServiceClient;
import wdm.project.service.clients.StocksServiceClient;
import wdm.project.service.clients.UsersServiceClient;

@Service
public class OrdersService {

//	@Autowired
//	private StocksServiceClient stocksServiceClient;
//	@Autowired
//	private UsersServiceClient usersServiceClient;
//	@Autowired
//	private PaymentsServiceClient paymentsServiceClient;
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private OrderItemRepository ordersItemsRepository;

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
        if (ordersRepository.contains(orderId)) {
        	//FIXME: remove the orderItems also?
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
     * order with the provided ID is not found
     */
    public OrdersWrapper findOrder(String orderId) throws OrderException {
		if (orderId == null) {
			throw new OrderException("The order id was not provided");
		}
        OrdersWrapper ordersWrapper = new OrdersWrapper();

        Order order  = ordersRepository.get(orderId);
//        Order order = orderOptional.orElseThrow(
//				        () -> new OrderException("Order with ID " + orderId + " not found.", HttpStatus.NOT_FOUND));

        Set<OrderItem> orderItems = order.getOrderItems();
        System.out.println(orderItems.size());
//        List<ItemInfo> itemsInfo = new ArrayList<>();
//	    for (OrderItem item : orderItems) {
//		    ItemInfo itemInfo = new ItemInfo(item.getItemId(), item.getAmount());
//		    itemsInfo.add(itemInfo);
//	    }

        ordersWrapper.setOrderItems(orderItems);
        ordersWrapper.setPaymentStatus("SUCCESSFUL"); // TODO call the payment microservice for that
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
    public void addItem(OrderItem requestOrderItem, String orderId, String itemId) throws OrderException {
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

        //TODO: Items price could be stored here when we call the stock service so we dont have to call again
        //OrderItemId orderItemId = new OrderItemId(orderId, itemId);
//        if (ordersRepository.contains(orderId)) {
//            //Optional<OrderItem> orderItemOptional = ordersItemsRepository.findById(orderItemId);
//            OrderItem orderItem = orderItemOptional.orElseThrow(
//		            () -> new OrderException("There was no order item with such id", HttpStatus.NOT_FOUND));
//            int amount = orderItem.getAmount() + requestOrderItem.getAmount();
//            orderItem.setAmount(amount);
//            ordersItemsRepository.save(orderItem);
//        } else {
            Order order = ordersRepository.get(orderId);
            OrderItem orderItem = new  OrderItem();
            //orderItem.setId(orderId, itemId);
            orderItem.setItemId(itemId);
            orderItem.setAmount(requestOrderItem.getAmount());

            Set<OrderItem> orderItems = order.getOrderItems();
            orderItems.add(orderItem); // FIXME check an yparxei
            order.setOrderItems(orderItems);

            ordersRepository.update(order);
//        }
    }

    /**
     * Removes a specified item from a specified order.
     *
     * @param orderId the id of the order that the item is removed from
     * @param itemId the id of the item that is to be removed
     * @throws OrderException when the OrderItem with provided IDs cannot be found.
     */
    public void removeItem(String orderId, String itemId) throws OrderException {
//        OrderItemId orderItemId = new OrderItemId(orderId, itemId);
//        if (ordersItemsRepository.contains().existsById(orderItemId)) {
//            ordersItemsRepository.deleteById(orderItemId);
//        } else {
//            throw new OrderException("Unable to remove item with ID " + itemId + " from order with ID " + orderId + ".", HttpStatus.NOT_FOUND);
//        }
    }

    /**
     * Checks-out an order invoking every other micro-service.
     *
     * @param orderId the id of order
     * @return the status of the transaction SUCCESS for a
     * successful transaction
     * FAILURE for a failed transaction.
     */
    public String checkoutOrder(String orderId){
        // TODO: connect everything
        return "FAILURE";
    }
}
