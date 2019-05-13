package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wdm.project.dto.Payment;

public interface PaymentsRepository extends JpaRepository<Payment, > {
}
