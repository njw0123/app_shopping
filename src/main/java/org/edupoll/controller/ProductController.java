package org.edupoll.controller;

import java.util.List;

import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<?> getProductsInfo(@RequestParam(defaultValue = "남성 의류") String productMainType,
			@RequestParam(defaultValue = "1") int page) {

		List<ProductWrapper> list = productService.allItems(page);
		log.info("상품 : {}", list.toString());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
