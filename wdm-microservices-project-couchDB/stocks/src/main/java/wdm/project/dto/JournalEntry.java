package wdm.project.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;
import wdm.project.enums.Status;

@TypeDiscriminator("doc.myField === 'event'")
public class JournalEntry  extends CouchDbDocument {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("price")
    private Integer price;

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

    public JournalEntry(String id, Status status, Integer price) {
        this.id = id;
        this.status = status.getValue();
        this.type = "event";
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

    /**
     * Sets new id.
     */
    public void setId(String transactionId, String event) {
        this.id = transactionId + "-" + event;
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
     * Gets price.
     *
     * @return Value of price.
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets new price.
     *
     * @param price New value of price.
     */
    public void setPrice(Integer price) {
        this.price = price;
    }


}
