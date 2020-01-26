
package com.myRetail.productservice.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
public class ProductData {

	@JsonProperty("id")
	private int id;

	@JsonProperty("name")
	private String title;

	@JsonProperty("current_price")
	private PriceData priceData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PriceData getPriceData() {
		return priceData;
	}

	public void setPriceData(PriceData priceData) {
		this.priceData = priceData;

	}

	@Override
	public String toString() {
		return "ProductData{" +
				"id=" + id +
				", title='" + title + '\'' +
				", priceData=" + priceData +
				'}';
	}
}