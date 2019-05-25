package wdm.project.dto.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemInfo {

	@JsonProperty("itemId")
	private Long id;

	@JsonProperty("amount")
	private Integer amount;

	public ItemInfo() {
	}

	public ItemInfo(Long itemId, Integer amount){
		this.id = itemId;
		this. amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
