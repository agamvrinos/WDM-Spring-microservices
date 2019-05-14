package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import wdm.project.dto.OrdersWrapper;
import wdm.project.repository.OrdersItemsRepository;
import wdm.project.repository.OrdersRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersItemsRepository ordersItemsRepository;

    public Long createOrder(Long userId) {
        try{
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

    public void removeOrder(Long orderId){
        try{
            ordersRepository.deleteById(orderId);
        } catch (RuntimeException exc){
            // TODO: Check if resource does not exist
            throw new RuntimeException("Unable to delete order: "+orderId+"(order_id)");
        }
    }

    public OrdersWrapper findOrder(Long orderId){

        OrdersWrapper ordersWrapper = new OrdersWrapper();

        Order order  = ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));

        ordersWrapper.setOrderItems(ordersItemsRepository.findAllOrderItems(orderId));
        ordersWrapper.setPaymentStatus("SUCCESSFUL"); // TODO call the payment microservice for that
        ordersWrapper.setUserId(order.getUserId());

        return ordersWrapper;
    }

    public void addItem(OrderItem requestOrderItem, Long orderId, Long itemId){
        //TODO: Items price could be stored here when we call the stock service so we dont have to call again
        try{
            OrderItem orderItem = new  OrderItem();
            orderItem.setId(orderId, itemId);
            orderItem.setAmount(requestOrderItem.getAmount());
            ordersItemsRepository.save(orderItem);
        } catch (RuntimeException exc){
            // TODO: Check if resource already exists
            throw new RuntimeException("Unable to add item: "+itemId+"(item_id) to order: "+orderId+"(order_id)");
        }
    }

    public void removeItem(Long orderId, Long itemId){
        try{
            ordersItemsRepository.deleteById(new OrderItemId(orderId, itemId));
        } catch (RuntimeException exc){
            // TODO: Check if resource does not exist
            throw new RuntimeException("Unable to remove item: "+itemId+"(item_id) from order: "+orderId+"(order_id)");
        }
    }

    public String checkoutOrder(Long orderId){
        // TODO: connect everything
        return "FAILURE";
    }

    public class OrderNotFoundException extends RuntimeException {
        OrderNotFoundException(Long id) {super("Order with id: " + id+ " does not exist");}
    }

}
