package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrderItem, OrderItemId> {

}
