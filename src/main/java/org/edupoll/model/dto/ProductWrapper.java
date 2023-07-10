package org.edupoll.model.dto;

import org.edupoll.model.entity.Product;

import lombok.Data;

@Data
public class ProductWrapper {

	private Long id;

	private String productMainType;
	private String productSubType;

	private String productName;
	private String explanation;

	private Integer price;
	private Integer inventory;
	private Integer salesRate;

	public ProductWrapper(Product product) {
		this.id = product.getProductId();
		this.productMainType = product.getProductMainType();
		this.productSubType = product.getProductSubType();
		this.productName = product.getProductName();
		this.explanation = product.getExplanation();
		this.price = product.getPrice();
		this.inventory = product.getInventory();
		this.salesRate = product.getSalesRate();
	}

}
