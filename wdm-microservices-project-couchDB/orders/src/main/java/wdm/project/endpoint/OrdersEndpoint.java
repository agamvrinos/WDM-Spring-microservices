package wdm.project.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.ItemInfo;
import wdm.project.dto.OrdersWrapper;
import wdm.project.exception.OrderException;
import wdm.project.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/create/{user_id}")
    public String createOrder(@PathVariable("user_id") String userId) throws OrderException {
        return ordersService.createOrder(userId);
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
            @RequestBody ItemInfo requestOrderItem,
            @PathVariable("order_id") String orderId,
            @PathVariable("item_id") String itemId
    ) throws OrderException {
        ordersService.addItem(requestOrderItem, orderId, itemId);
    }

    @DeleteMapping("/removeItem/{order_id}/{item_id}")
    public void removeItem(
            @PathVariable("order_id") String orderId,
            @PathVariable("item_id") String itemId
    ) throws OrderException {
        ordersService.removeItem(orderId, itemId);
    }

    @PostMapping("/checkout/{order_id}")
    public void checkoutOrder(@PathVariable("order_id") String orderId) throws OrderException {
        ordersService.checkoutOrder(orderId);
    }
}
