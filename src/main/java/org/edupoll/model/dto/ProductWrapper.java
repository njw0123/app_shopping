package org.edupoll.model.dto;

import java.util.List;

import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.ProductAttach;
import org.edupoll.model.entity.Review;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {

	private Long id;

	private String productMainType;
	private String productSubType;
	private String productName;
	private String explanation;
	private Integer price;
	private Integer inventory;
	private Integer salesRate;
	private List<Review> reviews;

	private List<ProductAttach> productAttachs;

	public ProductWrapper(Product product) {
		this.id = product.getId();
		this.productMainType = product.getProductMainType();
		this.productSubType = product.getProductSubType();
		this.productName = product.getProductName();
		this.explanation = product.getExplanation();
		this.price = product.getPrice();
		this.inventory = product.getInventory();
		this.salesRate = product.getSalesRate();
	}

	public ProductWrapper(Product product, List<Review> reviews) {
		this.id = product.getId();
		this.productMainType = product.getProductMainType();
		this.productSubType = product.getProductSubType();
		this.productName = product.getProductName();
		this.explanation = product.getExplanation();
		this.price = product.getPrice();
		this.inventory = product.getInventory();
		this.salesRate = product.getSalesRate();
		this.reviews = reviews;
	}

}
