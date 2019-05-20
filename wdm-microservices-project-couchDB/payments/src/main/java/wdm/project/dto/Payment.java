package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;

public class Payment extends CouchDbDocument {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("status")
    private String status;

    public Payment() {
    }

    /**
     * Gets userId.
     *
     * @return Value of userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets new userId.
     *
     * @param userId New value of userId.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets orderId.
     *
     * @return Value of orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets new orderId.
     *
     * @param orderId New value of orderId.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets status.
     *
     * @return Value of status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets new status.
     *
     * @param status New value of status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
