package wdm.project.dto;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Payment {

    @Id
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Status status;

    public Payment() {
    }

    /**
     * Gets status.
     *
     * @return Value of status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets new status.
     *
     * @param status New value of status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets orderId.
     *
     * @return Value of orderId.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets new orderId.
     *
     * @param orderId New value of orderId.
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets userId.
     *
     * @return Value of userId.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets new userId.
     *
     * @param userId New value of userId.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
