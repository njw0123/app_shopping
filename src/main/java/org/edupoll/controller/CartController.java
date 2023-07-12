package org.edupoll.controller;

import java.util.List;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartAndPurchaseRequest;
import org.edupoll.model.dto.response.ErrorResponse;
import org.edupoll.model.dto.response.UserResponse;
import org.edupoll.model.entity.Cart;
import org.edupoll.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/cart")
@Tag(name = "CartController", description = "장바구니에 관한 컨트롤러입니다.")
public class CartController {
	private final CartService cartService;
	
	// 장바구니에 담기
	@PostMapping
	@Operation(summary = "장바구니 추가", description = "상품을 장바구니에 담기 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "장바구니 추가 처리 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "물품 또는 아이디를 찾지못해 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> createCartHandle(@AuthenticationPrincipal String userId, CartAndPurchaseRequest req) throws NotFoundProductException, ExistUserException {
		cartService.create(userId, req);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	// 장바구니 목록 불러오기
	@GetMapping
	@Operation(summary = "장바구니 목록 불러오기", description = "장바구니에 목록을 불러오기 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "장바구니 목록 불러오기 성공", content = @Content(schema = @Schema(implementation = Cart.class))),
			@ApiResponse(responseCode = "400", description = "아이디 또는 물품을 찾지못해 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> getCartList(@AuthenticationPrincipal String userId) throws ExistUserException {	
		
		return new ResponseEntity<>(cartService.allList(userId), HttpStatus.OK);
	}
	
	// 장바구니에서 제거
	@DeleteMapping
	@Operation(summary = "장바구니 특정 목록 제거", description = "장바구니에 특정 목록을 제거하기 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "장바구니 특정 목록 제거 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "아이디 또는 물품을 찾지못해 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> deleteCartHandle(@AuthenticationPrincipal String userId, Long productId) throws ExistUserException, NotFoundProductException {
		cartService.delete(userId, productId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
