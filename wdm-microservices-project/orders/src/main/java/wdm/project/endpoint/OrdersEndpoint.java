package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrdersWrapper;
import wdm.project.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create/{user_id}")
    public JsonNode createOrder(@PathVariable("user_id") Long userId) throws  RuntimeException{
        return objectMapper.createObjectNode().put("order_id", ordersService.createOrder(userId));
    }

    @DeleteMapping("/remove/{order_id}")
    public void removeOrder(@PathVariable("order_id") Long orderId) throws RuntimeException {
        ordersService.removeOrder(orderId);
    }

    @GetMapping("/find/{order_id}")
    public OrdersWrapper findOrder(@PathVariable("order_id") Long orderId) throws RuntimeException {
        return ordersService.findOrder(orderId);
    }

    @PostMapping("/addItem/{order_id}/{item_id}")
    public void addItem(
            @RequestBody OrderItem requestOrderItem,
            @PathVariable("order_id") Long orderId,
            @PathVariable("item_id") Long itemId
    ) throws RuntimeException {
        // TODO call stock service for item information and not @RequestBody
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
    public JsonNode checkoutOrder(@PathVariable("order_id") Long orderId) {
        // TODO call everything
        return objectMapper.createObjectNode().put("status", ordersService.checkoutOrder(orderId));
    }
}