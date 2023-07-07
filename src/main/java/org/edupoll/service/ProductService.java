package org.edupoll.service;

import java.util.List;

import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.entity.Product;
import org.edupoll.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public List<ProductWrapper> allItems(int page) {

		List<Product> products = productRepository
				.findAll(PageRequest.of(page - 1, 10, Sort.by(Direction.DESC, "salesRate"))).toList();

		return products.stream().map(e -> new ProductWrapper(e)).toList();
	}

	public Long totalCount() {

		return productRepository.count();
	}

}
