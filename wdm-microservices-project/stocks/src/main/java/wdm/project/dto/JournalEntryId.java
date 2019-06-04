package wdm.project.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import wdm.project.enums.Event;

@Embeddable
public class JournalEntryId implements Serializable {

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "event", length = 100)
    private String event;

    public JournalEntryId() {
    }

    public JournalEntryId(Long orderId, Event event) {
        this.transactionId = orderId;
        this.event = event.getValue();
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntryId that = (JournalEntryId) o;
        return Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, event);
    }
}
