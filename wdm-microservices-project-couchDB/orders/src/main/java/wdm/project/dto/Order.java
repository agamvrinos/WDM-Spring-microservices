package wdm.project.dto;

import java.io.Serializable;

public class Order implements Serializable {

    private Long id;
    private Long userId;
    private Integer total;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id='" + this.userId + '\'' +
                ", total=" + this.total +
                '}';
    }
}
