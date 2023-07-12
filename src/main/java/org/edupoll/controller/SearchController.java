package org.edupoll.controller;

import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.response.ErrorResponse;
import org.edupoll.model.dto.response.ProductListResponse;
import org.edupoll.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "SearchController", description = "상품 검색에 관한 컨트롤러입니다.")
public class SearchController {
	private final ProductService productService;
	
	@GetMapping("/search")
	@Operation(summary = "상품 검색 기능", description = "상품을 검색하기 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "상품 불러오기 성공", content = @Content(schema = @Schema(implementation = ProductListResponse.class))),
			@ApiResponse(responseCode = "400", description = "상품 불러오기 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> searchHandle(String query) throws NotFoundProductException {		
		return new ResponseEntity<>(productService.search(query), HttpStatus.OK);
	}
}
