package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order extends CouchDbDocument {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("total")
    private Integer total;
//  LAZY = fetch when needed
//  EAGER = fetch immediately
    @JsonProperty("items")
    @DocumentReferences(fetch = FetchType.LAZY, backReference = "orderId")
    private Set<OrderItem> orderItems;

    public Order() {
       // this.orderItems = new HashSet<>();
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + super.getId() +
                ", user_id='" + this.userId + '\'' +
                ", total=" + this.total +
                '}';
    }
}
