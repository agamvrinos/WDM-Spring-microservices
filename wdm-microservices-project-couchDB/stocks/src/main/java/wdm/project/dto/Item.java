package wdm.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Item extends CouchDbDocument {

	@TypeDiscriminator
	@JsonProperty("_id")
	private String id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("stock")
	private Integer stock;

	@JsonProperty("price")
	private Integer price;

	public Item() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				"title='" + title + '\'' +
				", stock=" + stock +
				", price=" + price +
				'}';
	}
}
