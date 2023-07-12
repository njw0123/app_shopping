package org.edupoll.model.dto.request;

import lombok.Data;

@Data
public class CartAndPurchaseRequest {

	private Long productId; // 상품 ID
	private Integer quantity; // 수량

}
