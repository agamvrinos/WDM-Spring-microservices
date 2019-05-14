package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wdm.project.dto.OrderItem;
import wdm.project.dto.OrderItemId;

import java.util.List;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrderItem, OrderItemId> {

    @Query(value = "select oi.item_id from order_items oi where oi.order_id = :oid", nativeQuery = true)
    List<Long> findAllOrderItems(@Param("oid") Long order_id);

}
