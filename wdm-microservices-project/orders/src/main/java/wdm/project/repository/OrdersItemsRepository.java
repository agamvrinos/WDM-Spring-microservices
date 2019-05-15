package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;
import javax.transaction.Transactional;

import java.util.List;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrderItem, OrderItemId> {

    List<OrderItem> findAllById_OrderId(Long orderId);

    @Transactional
    //FIXME: This is madness!!
    void deleteById_OrderId(Long orderId);

}
