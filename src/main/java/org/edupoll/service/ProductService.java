package org.edupoll.service;

import java.util.List;

import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.entity.Product;
import org.edupoll.repository.ProductRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public List<ProductWrapper> allItems(String productMainType, String productSubType, int page) {

		List<Product> products = productRepository.findByProductSubTypeOrderBySalesRateDesc(productSubType);

		return products.stream().map(e -> new ProductWrapper(e)).toList();
	}

	public Long totalCount() {

		return productRepository.count();
	}

	public ProductWrapper getSpecificProduct(String productId) throws NotFoundProductException {
		Product found = productRepository.findByProductId(productId)
				.orElseThrow(() -> new NotFoundProductException("존재하지 않은 상품 ID 입니다."));

		return new ProductWrapper(found);
	}

}
