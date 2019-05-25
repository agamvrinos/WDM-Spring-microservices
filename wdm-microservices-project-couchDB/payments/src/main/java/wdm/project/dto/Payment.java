package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Payment extends CouchDbDocument {

    @TypeDiscriminator
    @JsonProperty("_id")
    private String id;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("status")
    private String status;

    public Payment() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
