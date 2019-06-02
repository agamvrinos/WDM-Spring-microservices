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
				", type='" + type + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	private Integer price;

	public JournalEntry() {
		this.type = "event";
	}

	public JournalEntry(String id, String status, Integer price) {
		this.type = "event";
		this.id = id;
		this.status = status;
		this.price = price;
	}

	public JournalEntry(String id, Status status, Integer price) {
		this.type = "event";
		this.id = id;
		this.status = status.getValue();
		this.price = price;
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