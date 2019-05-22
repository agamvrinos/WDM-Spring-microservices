package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdersWrapper {

    @JsonProperty("status")
    private String paymentStatus;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("items")
    private Set<OrderItem> orderItems;

    public OrdersWrapper(){
        this.orderItems = new HashSet<>();
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

	@Override
	public String toString() {
		return "OrdersWrapper{" +
				"paymentStatus='" + paymentStatus + '\'' +
				", userId=" + userId +
				", orderItems=" + orderItems +
				'}';
	}
}
