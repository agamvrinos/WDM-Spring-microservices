package wdm.project.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import wdm.project.enums.Status;

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

    public JournalEntry(String id, Status status) {
        this.id = id;
        this.status = status.getValue();
        this.type = "event";
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
    public void setId(String id) {
        this.id = id;
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
