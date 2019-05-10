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

    public Integer createOrder(Order requestOrder, Integer userId) {
        //TODO: Items? Total?
            Order order = new Order();
            order.setUserId(userId);
            order.setTotal(requestOrder.getTotal());
            ordersRepository.save(order);

            return  order.getId();
    }

    public void removeOrder(Integer orderId){
        ordersRepository.deleteById(orderId);
    }

    public Optional<Order> findOrder(Integer orderId){
        return ordersRepository.findById(orderId);
    }


    public void addItem(OrderItem requestOrderItem, Integer orderId, Integer itemId){
        OrderItem orderItem = new  OrderItem();
        orderItem.setId(orderId, itemId);
        orderItem.setAmount(requestOrderItem.getAmount());
        ordersItemsRepository.save(orderItem);
    }

    public void removeItem(Integer orderId, Integer itemId){
        ordersItemsRepository.deleteById(new OrderItemId(orderId, itemId));
    }

    public void checkoutOrder(Integer orderId){
        // TODO: connect everything
    }

}
