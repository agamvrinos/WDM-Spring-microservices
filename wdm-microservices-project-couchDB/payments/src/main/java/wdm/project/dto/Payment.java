package wdm.project.dto;

public class Payment {

    private Long id;
    private Long userId;
    private Long orderId;
    private String status;

    public Payment() {
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets userId.
     *
     * @return Value of userId.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets new userId.
     *
     * @param userId New value of userId.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets orderId.
     *
     * @return Value of orderId.
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Sets new orderId.
     *
     * @param orderId New value of orderId.
     */
    public void setOrderId(Long orderId) {
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
