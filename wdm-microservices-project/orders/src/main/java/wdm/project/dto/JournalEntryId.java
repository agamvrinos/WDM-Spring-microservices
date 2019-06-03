package wdm.project.dto;

import wdm.project.enums.Event;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JournalEntryId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "event", length = 100)
    private String event;

    public JournalEntryId() {

    }

    public JournalEntryId(Long orderId, String event) {
        this.orderId = orderId;
        this.event = event;
    }

    public JournalEntryId(Long orderId, Event event) {
        this.orderId = orderId;
        this.event = event.getValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntryId that = (JournalEntryId) o;
        return Objects.equals(orderId, that.orderId) &&
               Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, event);
    }

    /**
     * Sets new event.
     *
     * @param event New value of event.
     */
    public void setEvent(String event) {
        this.event = event;
    }

    public void setEvent(Event event) {
        this.event = event.getValue();
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
     * Gets event.
     *
     * @return Value of event.
     */
    public String getEvent() {
        return event;
    }
}
