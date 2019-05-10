package wdm.project.endpoint;

import org.springframework.web.bind.annotation.*;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    private final OrdersService ordersService;

    public OrdersEndpoint(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/create/{user_id}")
    public Integer createOrder(
            @RequestBody Order requestOrder,
            @PathVariable("user_id") Integer userId
    ) {
        return ordersService.createOrder(requestOrder, userId);
    }

    @DeleteMapping("/remove/{order_id}")
    public void removeOrder(@PathVariable("order_id") Integer orderId) {
        ordersService.removeOrder(orderId);
    }

    @GetMapping("/find/{order_id}")
    public Order findOrder(@PathVariable("order_id") Integer orderId) {
        return ordersService.findOrder(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }

    @PostMapping("/addItem/{order_id}/{item_id}")
    public void addItem(
            @RequestBody OrderItem requestOrderItem,
            @PathVariable("order_id") Integer orderId,
            @PathVariable("item_id") Integer itemId
    ) {
        ordersService.addItem(requestOrderItem, orderId, itemId);
    }

    @DeleteMapping("/removeItem/{order_id}/{item_id}")
    public void removeItem(
            @PathVariable("order_id") Integer orderId,
            @PathVariable("item_id") Integer itemId
    ) {
        ordersService.removeItem(orderId, itemId);
    }

    @PostMapping("/orders/checkout/{order_id}")
    public void checkoutOrder(@PathVariable("order_id") Integer orderId) {
        ordersService.checkoutOrder(orderId);
    }

    public class OrderNotFoundException extends RuntimeException {
        OrderNotFoundException(Integer id) {super("Order with id: " + id+ " does not exist");}
    }

}
