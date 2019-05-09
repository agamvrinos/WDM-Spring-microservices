package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.Payment;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Integer> {

}
