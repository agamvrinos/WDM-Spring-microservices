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

    /**
     * Returns the payment instance that corresponds to the
     * order with the provided {@code orderId}. In case there is no
     * payment instance for the particular {@code orderId} a mock
     * payment with "Pending" status is returned.
     *
     * @param orderId the id of the order
     * @return the Payment with the provided order id or a mock
     * "Pending" payment
     */
    public Payment getPaymentByOrderId(Long orderId) {
        if (orderId == null) {
            throw new RuntimeException("Id was not provided");
        }
        Payment payment;
        Boolean exists = paymentsRepository.existsByOrderId(orderId);
        if (exists) {
            payment = paymentsRepository.findByOrderId(orderId);
        } else {
            payment = new Payment();
            payment.setOrderId(orderId);
            payment.setStatus(Status.PENDING.getValue());
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
            //TODO: Update communication
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
