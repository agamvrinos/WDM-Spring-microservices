package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import java.util.ArrayList;
import java.util.List;

public class Order extends CouchDbDocument {

    @JsonProperty("type")
    private String type;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("items")
    private List<ItemInfo> orderItems;

    public Order() {
        this.type = "object";
        this.orderItems = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ItemInfo> getOrderItems() { return orderItems; }

    public void setOrderItems(List<ItemInfo> orderItems) { this.orderItems = orderItems; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + super.getId() +
                ", type='" + type + '\'' +
                ", userId='" + this.userId + '\'' +
                ", total=" + this.total +
                '}';
    }
}
