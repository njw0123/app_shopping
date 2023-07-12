package org.edupoll.controller;

import org.edupoll.exception.NotFoundProductException;
import org.edupoll.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SearchController {
	private final ProductService productService;
	
	@GetMapping("/search")
	public ResponseEntity<?> searchHandle(String query) throws NotFoundProductException {
		
		return new ResponseEntity<>(productService.search(query), HttpStatus.OK);
	}
}
