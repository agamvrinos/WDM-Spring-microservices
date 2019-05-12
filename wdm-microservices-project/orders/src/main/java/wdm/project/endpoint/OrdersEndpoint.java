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
    ) {

        try{ return ordersService.createOrder(requestOrder, userId);} catch (RuntimeException exc) {
            // TODO: Check if resource already exists
            throw new RuntimeException("Unable to create new order for user: " + userId + "(user_id)");
        }
    }

    @DeleteMapping("/remove/{order_id}")
    public void removeOrder(@PathVariable("order_id") Long orderId) {
        try{ ordersService.removeOrder(orderId); } catch (RuntimeException exc){
            // TODO: Check if resource does not exist
            throw new RuntimeException("Unable to delete order: "+orderId+"(order_id)");
        }
    }

    @GetMapping("/find/{order_id}")
    public Order findOrder(@PathVariable("order_id") Long orderId) {
        return ordersService.findOrder(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId));
    }

    @PostMapping("/addItem/{order_id}/{item_id}")
    public void addItem(
            @RequestBody OrderItem requestOrderItem,
            @PathVariable("order_id") Long orderId,
            @PathVariable("item_id") Long itemId
    ) {
        try{ ordersService.addItem(requestOrderItem, orderId, itemId); } catch (RuntimeException exc){
            // TODO: Check if resource already exists
            throw new RuntimeException("Unable to add item: "+itemId+"(item_id) to order: "+orderId+"(order_id)");
        }
    }

    @DeleteMapping("/removeItem/{order_id}/{item_id}")
    public void removeItem(
            @PathVariable("order_id") Long orderId,
            @PathVariable("item_id") Long itemId
    ) {
        try{ ordersService.removeItem(orderId, itemId); } catch (RuntimeException exc){
            // TODO: Check if resource does not exist
            throw new RuntimeException("Unable to remove item: "+itemId+"(item_id) from order: "+orderId+"(order_id)");
        }

    }

    @PostMapping("/orders/checkout/{order_id}")
    public void checkoutOrder(@PathVariable("order_id") Long orderId) {
        ordersService.checkoutOrder(orderId);
    }

    public class OrderNotFoundException extends RuntimeException {
        OrderNotFoundException(Long id) {super("Order with id: " + id+ " does not exist");}
    }

}
