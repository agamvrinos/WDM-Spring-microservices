package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/create/{user_id}")
    public Long createOrder(
            @RequestBody Order requestOrder,
            @PathVariable("user_id") Long userId
    ) throws  RuntimeException{
        return ordersService.createOrder(requestOrder, userId);
    }

    @DeleteMapping("/remove/{order_id}")
    public void removeOrder(@PathVariable("order_id") Long orderId) throws RuntimeException {
        ordersService.removeOrder(orderId);
    }

    @GetMapping("/find/{order_id}")
    public Order findOrder(@PathVariable("order_id") Long orderId) throws OrderNotFoundException {
        return ordersService.findOrder(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }

    @PostMapping("/addItem/{order_id}/{item_id}")
    public void addItem(
            @RequestBody OrderItem requestOrderItem,
            @PathVariable("order_id") Long orderId,
            @PathVariable("item_id") Long itemId
    ) throws RuntimeException {
        ordersService.addItem(requestOrderItem, orderId, itemId);
    }

    @DeleteMapping("/removeItem/{order_id}/{item_id}")
    public void removeItem(
            @PathVariable("order_id") Long orderId,
            @PathVariable("item_id") Long itemId
    ) throws RuntimeException {
        ordersService.removeItem(orderId, itemId);
    }

    @PostMapping("/orders/checkout/{order_id}")
    public void checkoutOrder(@PathVariable("order_id") Long orderId) {
        ordersService.checkoutOrder(orderId);
    }

    public class OrderNotFoundException extends RuntimeException {
        OrderNotFoundException(Long id) {super("Order with id: " + id+ " does not exist");}
    }

}
