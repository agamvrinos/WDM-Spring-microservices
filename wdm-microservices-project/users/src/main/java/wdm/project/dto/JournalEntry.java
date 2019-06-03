package wdm.project.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import wdm.project.enums.Status;

@Entity
@Table(name = "journal", schema = "public")
public class JournalEntry {

    @EmbeddedId
    private JournalEntryId id;

    @Column(name = "status", length = 100)
    private String status;

    public JournalEntry() {
    }

    public JournalEntry(JournalEntryId id, Status status) {
        this.id = id;
        this.status = status.getValue();
    }

    public JournalEntryId getId() {
        return id;
    }

    public void setId(JournalEntryId id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
