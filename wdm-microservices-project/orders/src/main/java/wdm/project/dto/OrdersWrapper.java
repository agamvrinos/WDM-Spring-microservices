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
    private List<IdAmountWrapper>  orderItems;

    class IdAmountWrapper{
        @JsonProperty("item_id")
        Long id;
        @JsonProperty("amount")
        Integer amount;
        IdAmountWrapper(Long itemId, Integer amount){
            this.id = itemId;
            this. amount = amount;
        }
    }

    public OrdersWrapper(){
        this.orderItems = new ArrayList<>();
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setOrderItems(List<OrderItem>  orderItems) {

        List<IdAmountWrapper>  wrapped = new ArrayList<>();

        for (OrderItem oi : orderItems){
            wrapped.add(new IdAmountWrapper(oi.getId().getItemId(), oi.getAmount()));
        }

        this.orderItems = wrapped ;
    }

    public String getPaymentStatus() { return paymentStatus; }

    public Long getUserId() { return userId; }

    public List<IdAmountWrapper> getOrderItems() { return orderItems; }
}
