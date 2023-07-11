package org.edupoll.model.dto.request;

import lombok.Data;

@Data
public class CartAndPurchaseRequest {
	private Long productId;
	private Integer quantity; 
}
