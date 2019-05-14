package wdm.project.dto;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "order_items", schema = "public")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemId id;

    @Column(name = "amount")
    private Integer amount;

    public OrderItem(){
        this.id = new OrderItemId();
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(Long orderId, Long itemId) {
        this.id.setOrderId(orderId);
        this.id.setItemId(itemId);
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "order_item_id=" + this.id + '\'' +
                ", amount=" + this.amount +
                '}';
    }

}
