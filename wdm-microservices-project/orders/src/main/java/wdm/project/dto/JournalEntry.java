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

	@Column(name = "price")
    private Integer price;

    public JournalEntry() {
    }

    public JournalEntry(JournalEntryId id, Status status, Integer price) {
        this.id = id;
        this.status = status.getValue();
        this.price = price;
    }

	public void setId(JournalEntryId id) {
		this.id = id;
	}

	public JournalEntryId getId() {
		return id;
	}

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public String getStatus() {
        return status;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

	@Override
	public String toString() {
		return "JournalEntry{" +
				"id=" + id +
				", status='" + status + '\'' +
				", price=" + price +
				'}';
	}
}
