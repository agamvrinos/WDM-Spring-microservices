package wdm.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import wdm.project.dto.Payment;
import wdm.project.dto.Status;
import wdm.project.repository.PaymentsRepository;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public Payment getPayment(Integer orderId) {
        if (paymentsRepository.existsById(orderId)) {
            return paymentsRepository.getOne(orderId);
        } else {
            Payment payment = new Payment();
            payment.setStatus(Status.pending);
            return payment;
        }
    }

    public Payment payOrder(Integer orderId, Integer userId, Integer totalPrice) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);

        Status paymentStatus = Status.failure;
        try {
            RestTemplate re = new RestTemplate();
            JsonNode response = re.postForObject("http://localhost:8083/users/credit/subtract/" + userId + "/" + totalPrice, null, JsonNode.class);
            paymentStatus = Status.valueOf(response.get("status").asText());
        } catch (RestClientResponseException exception) {
            // Handle by checking other instances in case of timeout.
        }
        payment.setStatus(paymentStatus);
        paymentsRepository.save(payment);
        return payment;
    }
}
