package wdm.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import wdm.project.dto.Payment;
import wdm.project.enums.Status;
import wdm.project.exception.PaymentException;
import wdm.project.repository.PaymentsRepository;
import wdm.project.service.clients.PaymentsServiceClient;
import wdm.project.service.clients.StocksServiceClient;
import wdm.project.service.clients.UsersServiceClient;

@Service
public class PaymentsService {

    @Autowired
    private StocksServiceClient stocksServiceClient;
    @Autowired
    private UsersServiceClient usersServiceClient;
    @Autowired
    private PaymentsServiceClient paymentsServiceClient;
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
     * @throws PaymentException when the provided order ID is null
     */
    public Payment getPaymentByOrderId(Long orderId) throws PaymentException {
        if (orderId == null) {
            throw new PaymentException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        Payment payment;
        if (paymentsRepository.existsByOrderId(orderId)) {
            payment = paymentsRepository.findByOrderId(orderId);
        } else {
            payment = new Payment();
            payment.setOrderId(orderId);
            payment.setStatus(Status.PENDING.getValue());
        }
        return payment;
    }

    /**
     * Pays for the order with the provided ID by reducing the total price
     * from the credit of the user with the provided ID.
     *
     * @param orderId the ID of the order
     * @param userId the ID of the user
     * @param totalPrice the price of the order to be paid by the user
     * @return the Payment with the provided order ID
     * @throws PaymentException when the communication with the Users
     * microservice has failed
     */
    public Payment payOrder(Long orderId, Long userId, Integer totalPrice) throws PaymentException {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);

        String paymentStatus = Status.FAILURE.getValue();
        try {
            //TODO: Update communication
            RestTemplate re = new RestTemplate();
            JsonNode response = re.postForObject("http://localhost:8083/users/credit/subtract/" + userId + "/" + totalPrice, null, JsonNode.class);
            String responseStatus = response.get("status").asText();
            if (responseStatus != null) {
                paymentStatus = Status.findStatusEnum(responseStatus).getValue();
            }
        } catch (RestClientResponseException exception) {
            // TODO: Handle by checking other instances in case of timeout.
            throw new PaymentException("There was an exception while communicating with the Users microservice.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        payment.setStatus(paymentStatus);
        return paymentsRepository.save(payment);
    }
}
