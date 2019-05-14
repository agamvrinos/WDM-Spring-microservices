package wdm.project.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Payment;
import wdm.project.enums.Status;
import wdm.project.service.PaymentsService;

@RestController
@RequestMapping("/payments")
public class PaymentsEndpoint {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/status/{order_id}")
    public JsonNode getPaymentStatus(@PathVariable("order_id") Long orderId) {
        Payment payment = paymentsService.getPaymentByOrderId(orderId);
        return objectMapper.createObjectNode().put("status", payment.getStatus());
    }

    @PostMapping("/pay/{order_id}/{user_id}/{total}")
    public void payOrder(
            @PathVariable("order_id") Long orderId,
            @PathVariable("user_id") Long userId,
            @PathVariable("total") Integer total) {
        if (userId == null || total == null) {
            throw new IllegalArgumentException("Params were not provided");
        }
        Payment payment = paymentsService.payOrder(orderId, userId, total);
        if (Status.findStatusEnum(payment.getStatus()).equals(Status.SUCCESS)) {
            // TODO: Return 400
            throw new RuntimeException();
        }
    }
}
