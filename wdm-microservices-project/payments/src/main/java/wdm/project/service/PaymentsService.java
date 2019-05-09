package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
