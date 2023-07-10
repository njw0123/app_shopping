package org.edupoll.model.dto.request;

import lombok.Data;

@Data
public class CartCreateRequest {
	private Long productId;
	private Integer quantity; 
}
