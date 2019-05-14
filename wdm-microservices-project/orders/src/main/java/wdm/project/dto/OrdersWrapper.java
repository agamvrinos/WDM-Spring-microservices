package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class OrdersWrapper{

    @JsonProperty("status")
    private String paymentStatus;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("items")
    private List<Long> orderItems;

    public OrdersWrapper(){
        this.orderItems = new ArrayList<>();
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Long> orderItems) {
        this.orderItems = orderItems;
    }
}
