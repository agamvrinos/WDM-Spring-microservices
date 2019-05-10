package wdm.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import wdm.project.dto.Payment;
import wdm.project.enums.Status;
import wdm.project.repository.PaymentsRepository;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public Payment getPayment(Long orderId) {
        Payment payment;
        if (paymentsRepository.existsByOrderId(orderId)) {
            payment = paymentsRepository.findByOrderId(orderId);
        } else {
            //TODO: Shouldn't we also store the user_id???
            payment = new Payment();
            payment.setOrderId(orderId);
            payment.setStatus(Status.PENDING.getValue());
            payment = paymentsRepository.save(payment);
        }
        return payment;
    }

    public Payment payOrder(Long orderId, Long userId, Long totalPrice) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);

        String paymentStatus = Status.FAILURE.getValue();
        try {
            RestTemplate re = new RestTemplate();
            JsonNode response = re.postForObject("http://localhost:8083/users/credit/subtract/" + userId + "/" + totalPrice, null, JsonNode.class);
            String responseStatus = response.get("status").asText();
            if (responseStatus != null) {
                paymentStatus = Status.findStatusEnum(responseStatus).getValue();
            }
        } catch (RestClientResponseException exception) {
            // Handle by checking other instances in case of timeout.
        }
        payment.setStatus(paymentStatus);
        return paymentsRepository.save(payment);
    }
}
