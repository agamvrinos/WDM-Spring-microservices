package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
