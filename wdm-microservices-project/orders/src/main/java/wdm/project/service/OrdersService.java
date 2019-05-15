package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.repository.OrdersItemsRepository;
import wdm.project.repository.OrdersRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersItemsRepository ordersItemsRepository;

    /**
     * Initializes an order in the micro-service's database with zero
     * total and a given user id that corresponds to the user this order
     * is linked to. Returns the id of the order that is created.
     *
     * @param userId the id of the user this order is linked to
     * @return the id of the order that is created
     */
    public Long createOrder(Long userId) {
        try{
            // TODO: Currently receives a duplicate PK, has to be fixed.
            Order order = new Order();
            order.setUserId(userId);
            order.setTotal(0);
            ordersRepository.save(order);

            return  order.getId();
        } catch (RuntimeException exc) {
            // TODO: Check if resource already exists
            throw new RuntimeException("Unable to create new order for user: " + userId + "(user_id)");
        }
    }

    /**
     * Removes an order in the micro-service's database that corresponds
     * to a give unique order id.
     *
     * @param orderId the id of the order to be deleted
     * @throws OrderException when the order with the provided ID is not found
     */
    public void removeOrder(Long orderId) throws OrderException {
        if (ordersRepository.existsById(orderId)) {
            // TODO: Remove all order items before deleting this, otherwise FK constraints are (possibly) violated.
            ordersRepository.deleteById(orderId);
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
     * @throws OrderException when the order with the provided ID is not found
     */
    public OrdersWrapper findOrder(Long orderId) throws OrderException {

        OrdersWrapper ordersWrapper = new OrdersWrapper();

        Order order  = ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderException("Order with ID " + orderId + " not found.", HttpStatus.NOT_FOUND));

        ordersWrapper.setOrderItems(ordersItemsRepository.findAllOrderItems(orderId));
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
    public void addItem(OrderItem requestOrderItem, Long orderId, Long itemId) {
        //TODO: Items price could be stored here when we call the stock service so we dont have to call again
        OrderItemId orderItemId = new OrderItemId(orderId, itemId);
        if (ordersItemsRepository.existsById(orderItemId)) {
            OrderItem orderItem = ordersItemsRepository.getOne(orderItemId);
            int amount = orderItem.getAmount() + requestOrderItem.getAmount();
            orderItem.setAmount(amount);
            ordersItemsRepository.save(orderItem);
        } else {
            OrderItem orderItem = new  OrderItem();
            orderItem.setId(orderId, itemId);
            orderItem.setAmount(requestOrderItem.getAmount());
            ordersItemsRepository.save(orderItem);
        }
    }

    /**
     * Removes a specified item from a specified order.
     *
     * @param orderId the id of the order that the item is removed from
     * @param itemId the id of the item that is to be removed
     * @throws OrderException when the OrderItem with provided IDs cannot be found.
     */
    public void removeItem(Long orderId, Long itemId) throws OrderException {
        OrderItemId orderItemId = new OrderItemId(orderId, itemId);
        if (ordersItemsRepository.existsById(orderItemId)) {
            ordersItemsRepository.deleteById(orderItemId);
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
    public String checkoutOrder(Long orderId){
        // TODO: connect everything
        return "FAILURE";
    }
}
