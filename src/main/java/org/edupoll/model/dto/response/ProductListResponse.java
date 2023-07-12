package org.edupoll.model.dto.response;

import java.util.List;

import org.edupoll.model.dto.ProductWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListResponse {

	private long total;
	private List<ProductWrapper> products;
}
