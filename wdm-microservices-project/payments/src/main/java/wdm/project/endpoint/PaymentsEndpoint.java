package wdm.project.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wdm.project.dto.Payment;
import wdm.project.enums.Status;
import wdm.project.exception.PaymentException;
import wdm.project.service.PaymentsService;

@RestController
@RequestMapping("/payments")
public class PaymentsEndpoint {

    @Autowired
    private PaymentsService paymentsService;

    @GetMapping("/status/{order_id}")
    public String getPaymentStatus(@PathVariable("order_id") Long orderId) throws PaymentException {
        Payment payment = paymentsService.getPaymentByOrderId(orderId);
        return payment.getStatus();
    }

    @PostMapping("/pay/{order_id}/{user_id}/{total}")
    public void payOrder(
            @PathVariable("order_id") Long orderId,
            @PathVariable("user_id") Long userId,
            @PathVariable("total") Integer total
    ) throws PaymentException {
        if (orderId == null || userId == null || total == null) {
            throw new PaymentException("Params were not provided", HttpStatus.BAD_REQUEST);
        }
        Payment payment = paymentsService.payOrder(orderId, userId, total);
        Status statusEnum = Status.findStatusEnum(payment.getStatus());
        if (statusEnum != Status.SUCCESS) {
            throw new PaymentException("Payment status is not \"SUCCESS\"", HttpStatus.BAD_REQUEST);
        }
    }
}
