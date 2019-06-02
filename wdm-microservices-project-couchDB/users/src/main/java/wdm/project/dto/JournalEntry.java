package wdm.project.dto;

import org.ektorp.support.CouchDbDocument;
import wdm.project.enums.Status;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

@TypeDiscriminator("doc._type === 'event'")
public class JournalEntry  extends CouchDbDocument {

	@JsonProperty("_id")
	private String id;

	@JsonProperty("type")
	private String type;

	@JsonProperty("status")
	private String status;

	@Override
	public String toString() {
		return "JournalEntry{" +
				"id=" + id +
				", status='" + status + '\'' +
				'}';
	}

	public JournalEntry() {
		this.type = "event";
	}

	public JournalEntry(String id, String status) {
		this.type = "event";
		this.id = id;
		this.status = status;
	}

	public JournalEntry(String id, Status status) {
		this.type = "event";
		this.id = id;
		this.status = status.getValue();
	}

	/**
	 * Gets id.
	 *
	 * @return Value of id.
	 */
	public String getId() {
		return id;
	}

	public void setId(String transactionId, String event) {
		this.id = transactionId + "-" + event;
	}

	/**
	 * Sets new id.
	 *
	 * @param id New value of id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}