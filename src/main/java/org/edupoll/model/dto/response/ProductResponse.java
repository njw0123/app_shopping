package org.edupoll.model.dto.response;

import java.util.List;

import org.edupoll.model.dto.ProductWrapper;

public class ProductResponse {

	private Long id;
	private String productMainType;
	private String productSubType;

	private String productName;

	private String explanation;

	private Integer price;

	private Integer inventory;

	private Integer salesRate;

	public ProductResponse(ProductWrapper product) {
		this.id = product.getId();
		this.productMainType = product.getProductMainType();
		this.productSubType = product.getProductSubType();
		this.productName = product.getProductName();
		this.explanation = product.getExplanation();
		this.price = product.getPrice();
		this.inventory = product.getInventory();
		this.salesRate = product.getSalesRate();

	}

}
