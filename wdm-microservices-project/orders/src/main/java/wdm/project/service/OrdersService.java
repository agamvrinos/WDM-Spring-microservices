package wdm.project.service;

import org.springframework.stereotype.Service;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import wdm.project.repository.OrdersItemsRepository;
import wdm.project.repository.OrdersRepository;

import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersItemsRepository ordersItemsRepository;

    public OrdersService(OrdersRepository ordersRepository, OrdersItemsRepository ordersItemsRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersItemsRepository = ordersItemsRepository;
    }

    public Long createOrder(Order requestOrder, Long userId) {
            Order order = new Order();
            order.setUserId(userId);
            order.setTotal(requestOrder.getTotal());
            ordersRepository.save(order);

            return  order.getId();
    }

    public void removeOrder(Long orderId){
        ordersRepository.deleteById(orderId);
    }

    public Optional<Order> findOrder(Long orderId){
        return ordersRepository.findById(orderId);
    }


    public void addItem(OrderItem requestOrderItem, Long orderId, Long itemId){
        //TODO: Items price could be stored here when we call the stock service so we dont have to call again
        OrderItem orderItem = new  OrderItem();
        orderItem.setId(orderId, itemId);
        orderItem.setAmount(requestOrderItem.getAmount());
        ordersItemsRepository.save(orderItem);
    }

    public void removeItem(Long orderId, Long itemId){
        ordersItemsRepository.deleteById(new OrderItemId(orderId, itemId));
    }

    public void checkoutOrder(Long orderId){
        // TODO: connect everything
    }

}
