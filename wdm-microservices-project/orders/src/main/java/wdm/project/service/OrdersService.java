package wdm.project.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.repository.OrdersItemsRepository;
import wdm.project.repository.OrdersRepository;
import wdm.project.service.clients.PaymentsServiceClient;
import wdm.project.service.clients.StocksServiceClient;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(rollbackFor = OrderException.class)
public class OrdersService {

    @Autowired
    private StocksServiceClient stocksServiceClient;
    @Autowired
    private PaymentsServiceClient paymentsServiceClient;
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
     * @return the order that is created
     * @throws OrderException in case the id of the user was not provided
     */
    public Order createOrder(Long userId) throws OrderException {
        if (userId == null) {
            throw new OrderException("User id was not provided", HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(0);
        ordersRepository.save(order);

        return order;
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
            ordersItemsRepository.deleteById_OrderId(orderId);
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
     * @throws OrderException when the order id was not provided or the
     *                        order with the provided ID is not found
     */
    public OrdersWrapper findOrder(Long orderId) throws OrderException {
        if (orderId == null) {
            throw new OrderException("The order id was not provided");
        }
        OrdersWrapper ordersWrapper = new OrdersWrapper();

        Optional<Order> orderOptional = ordersRepository.findById(orderId);
        Order order = orderOptional.orElseThrow(
                () -> new OrderException("Order with ID " + orderId + " not found.", HttpStatus.NOT_FOUND));

        List<OrderItem> orderItems = ordersItemsRepository.findAllById_OrderId(orderId);
        List<ItemInfo> itemsInfo = new ArrayList<>();
        for (OrderItem item : orderItems) {
            ItemInfo itemInfo = new ItemInfo(item.getId().getItemId(), item.getAmount());
            itemsInfo.add(itemInfo);
        }

        ordersWrapper.setOrderItems(itemsInfo);
        ordersWrapper.setUserId(order.getUserId());

        try {
            ordersWrapper.setPaymentStatus(paymentsServiceClient.getPaymentStatus(orderId));
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
    public void addItem(OrderItem requestOrderItem, Long orderId, Long itemId) throws OrderException {
        if (orderId == null) {
            throw new OrderException("Order id was not provided");
        }
        if (itemId == null) {
            throw new OrderException("Item id was not provided");
        }
        boolean existsOrder = ordersRepository.existsById(orderId);
        if (!existsOrder) {
            throw new OrderException("There is no order with it \"" + orderId + "\"");
        }

        if (!checkItem(itemId)) {
            throw new OrderException("Item id does not exist: ", HttpStatus.NOT_FOUND);
        }

        OrderItemId orderItemId = new OrderItemId(orderId, itemId);
        OrderItem emptyOrderItem = new OrderItem();
        emptyOrderItem.setId(orderId, itemId);
        emptyOrderItem.setAmount(0);

        OrderItem orderItem = ordersItemsRepository.findById(orderItemId).orElse(emptyOrderItem);
        orderItem.setAmount(orderItem.getAmount() + requestOrderItem.getAmount());
        ordersItemsRepository.save(orderItem);

    }

    public Boolean checkItem(Long itemId){
        return stocksServiceClient.checkItem(itemId);
    }

    /**
     * Removes a specified item from a specified order.
     *
     * @param orderId the id of the order that the item is removed from
     * @param itemId  the id of the item that is to be removed
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
     * successful transaction
     * FAILURE for a failed transaction.
     */
    public void checkoutOrder(Long orderId) throws OrderException {
        if (orderId == null) {
            throw new OrderException("The order id was not provided");
        }
        Long userId = getUserIdForCheckout(orderId);
        List<ItemInfo> orderItems = getOrderItemsForCheckout(orderId);
        Integer price;
        try {
            price = stocksServiceClient.subtractItems(orderItems);
        } catch (FeignException exception) {
            if (exception.status() == 400) {
                throw new OrderException(new String(exception.content()), HttpStatus.BAD_REQUEST);
            } else {
                throw new OrderException("Something went wrong when subtracting the stock.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            paymentsServiceClient.payOrder(orderId, userId, price);
        } catch (FeignException exception) {
            if (exception.status() == 400) {
                stocksServiceClient.addItems(orderItems);
                throw new OrderException(new String(exception.content()), HttpStatus.BAD_REQUEST);
            } else {
                throw new OrderException("Something went wrong when paying the order.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private Long getUserIdForCheckout(Long orderId) throws OrderException {
        return ordersRepository.findById(orderId).orElseThrow(
                () -> new OrderException("Order with ID " + orderId + " not found.", HttpStatus.NOT_FOUND)).getUserId();
    }

    private List<ItemInfo> getOrderItemsForCheckout(Long orderId) throws OrderException {
        try {
            return ordersItemsRepository.findAllById_OrderId(orderId)
                    .stream()
                    .map(this::transformOrderItemToItemInfo)
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e){
            throw new OrderException("There is no order with ID " + orderId + ".", HttpStatus.NOT_FOUND);
        }
    }

    private ItemInfo transformOrderItemToItemInfo(OrderItem orderItem){
        return new ItemInfo(orderItem.getId().getItemId(), orderItem.getAmount());
    }



}
