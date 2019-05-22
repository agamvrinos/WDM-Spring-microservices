package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Order;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create/{user_id}")
    public String createOrder(@PathVariable("user_id") String userId) throws OrderException {
        //Order order = ordersService.createOrder(userId);
        return ordersService.createOrder(userId);//objectMapper.createObjectNode().put("orderId", order.getId());
    }

    @DeleteMapping("/remove/{order_id}")
    public void removeOrder(@PathVariable("order_id") String orderId) throws OrderException {
        ordersService.removeOrder(orderId);
    }

    @GetMapping("/find/{order_id}")
    public OrdersWrapper findOrder(@PathVariable("order_id") String orderId) throws OrderException {
        return ordersService.findOrder(orderId);
    }

    @PostMapping("/addItem/{order_id}/{item_id}")
    public void addItem(
            @RequestBody OrderItem requestOrderItem,
            @PathVariable("order_id") String orderId,
            @PathVariable("item_id") String itemId
    ) throws OrderException {
        // TODO call stock service for item information and not @RequestBody
        ordersService.addItem(requestOrderItem, orderId, itemId);
    }

    @DeleteMapping("/removeItem/{order_id}/{item_id}")
    public void removeItem(
            @PathVariable("order_id") String orderId,
            @PathVariable("item_id") String itemId
    ) throws OrderException {
        ordersService.removeItem(orderId, itemId);
    }

    @PostMapping("/orders/checkout/{order_id}")
    public JsonNode checkoutOrder(@PathVariable("order_id") String orderId) {
        // TODO call everything
        String status = ordersService.checkoutOrder(orderId);
        return objectMapper.createObjectNode().put("status", status);
    }
}
