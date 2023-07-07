package org.edupoll.model.dto;

import org.edupoll.model.entity.Product;

public class ProductWrapper {

	private Long id;

	String productMainType;
	String productSubType;

	String productName;
	String explanation;

	Integer price;
	Integer inventory;
	Integer salesRate;

	public ProductWrapper(Product product) {
		this.productMainType = product.getProductMainType();
		this.productSubType = product.getProductSubType();
		this.productName = product.getProductName();
		this.explanation = product.getExplanation();
		this.price = product.getPrice();
		this.inventory = product.getInventory();
		this.salesRate = product.getSalesRate();
	}

}
