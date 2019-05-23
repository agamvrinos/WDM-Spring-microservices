package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class OrdersWrapper {

    @JsonProperty("status")
    private String paymentStatus;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("items")
    private List<ItemInfo> orderItems;

    public OrdersWrapper(){

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

    public List<ItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemInfo> orderItems) {
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
