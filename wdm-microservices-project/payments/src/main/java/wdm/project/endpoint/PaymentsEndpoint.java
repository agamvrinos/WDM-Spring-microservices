package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Payment;
import wdm.project.service.PaymentsService;

@RestController
@RequestMapping("/payments")
public class PaymentsEndpoint {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/status/{order_id}")
    public JsonNode getStatus(@PathVariable("order_id") Long orderId) {
        Payment payment = paymentsService.getPayment(orderId);
        return objectMapper.createObjectNode().put("status", payment.getStatus());
    }

    @PostMapping("/pay/{order_id}")
    public JsonNode payOrder(
            @PathVariable("order_id") Long orderId,
            @RequestParam("userId") Long userId,
            @RequestParam("total") Long total) {
        if (userId == null || total == null) {
            throw new IllegalArgumentException("Params were not provided");
        }
        Payment payment = paymentsService.payOrder(orderId, userId, total);
        return objectMapper.createObjectNode().put("status", payment.getStatus());
    }
}
