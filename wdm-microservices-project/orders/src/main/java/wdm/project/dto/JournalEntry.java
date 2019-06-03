package wdm.project.dto;

import wdm.project.enums.Status;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "journal", schema = "public")
public class JournalEntry {
    @EmbeddedId
    private JournalEntryId id;

    @Column(name = "status", length = 100)
    private String status;

    @Override
    public String toString() {
        return "JournalEntry{" +
               "id=" + id +
               ", status='" + status + '\'' +
               ", price=" + price +
               '}';
    }

    private Integer price;

    public JournalEntry() {

    }

    public JournalEntry(JournalEntryId id, String status, Integer price) {
        this.id = id;
        this.status = status;
        this.price = price;
    }

    public JournalEntry(JournalEntryId id, Status status, Integer price) {
        this.id = id;
        this.status = status.getValue();
        this.price = price;
    }

    /**
     * Sets new status.
     *
     * @param status New value of status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
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
     * Gets id.
     *
     * @return Value of id.
     */
    public JournalEntryId getId() {
        return id;
    }

    public void setId(Long orderId, String event) {
        this.id.setOrderId(orderId);
        this.id.setEvent(event);
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(JournalEntryId id) {
        this.id = id;
    }

    /**
     * Sets new price.
     *
     * @param price New value of price.
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Gets price.
     *
     * @return Value of price.
     */
    public Integer getPrice() {
        return price;
    }
}
