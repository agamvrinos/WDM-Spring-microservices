package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemInfo {

	@JsonProperty("itemId")
	private String id;

	@JsonProperty("amount")
	private Integer amount;

	public ItemInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ItemInfo{" +
				"id=" + id +
				", amount=" + amount +
				'}';
	}
}
