package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;

public class OrderItem extends CouchDbDocument {

    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("amount")
    private Integer amount;

    public OrderItem(){

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
                "order_item_id=" + this.itemId + '\'' +
                ", amount=" + this.amount +
                '}';
    }
}
