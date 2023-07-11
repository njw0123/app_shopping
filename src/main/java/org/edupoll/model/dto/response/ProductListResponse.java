package org.edupoll.model.dto.response;

import java.util.List;

import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.entity.Product;

import lombok.Data;

@Data
public class ProductListResponse {

	private long total;
	private List<Product> products;

	public ProductListResponse(long total, List<Product> products) {
		this.products = products;
		this.total = total;
	}

}
