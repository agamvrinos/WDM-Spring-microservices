package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

@TypeDiscriminator("doc._type === 'object'")
public class User extends CouchDbDocument {

	@JsonProperty("type")
	private String type;

	@JsonProperty("name")
	private String name;

	@JsonProperty("credit")
	private Integer credit;

	public User() {
		this.type = "object";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + super.getId() +
				", name='" + name + '\'' +
				", credit=" + credit +
				'}';
	}
}
