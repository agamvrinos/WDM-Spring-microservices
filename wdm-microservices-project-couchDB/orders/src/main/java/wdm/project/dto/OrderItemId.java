package wdm.project.dto;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemId implements Serializable {

    private Long orderId;
    private Long itemId;

    public OrderItemId() {
    }

    public OrderItemId(Long orderId, Long itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemId)) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(getOrderId(), that.getOrderId()) &&
                Objects.equals(getItemId(), that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getItemId());
    }
}
