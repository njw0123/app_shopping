package org.edupoll.controller;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartAndPurchaseRequest;
import org.edupoll.model.dto.response.ErrorResponse;
import org.edupoll.model.entity.Purchase;
import org.edupoll.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/purchase")
@Tag(name = "PurchaseController", description = "구매 내역에 관한 컨트롤러입니다.")
public class PurchaseController {
	private final PurchaseService purchaseService;

	// 구매 내역 생성
	@PostMapping
	@Operation(summary = "구매 내역 생성", description = "구매 내역 생성을 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "구매 내역 생성 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "구매 내역 생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> purchaseCreateHandle(@AuthenticationPrincipal String userId, CartAndPurchaseRequest req)
			throws ExistUserException, NotFoundProductException {
		purchaseService.create(userId, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 구매 내역 확인
	@GetMapping
	@Operation(summary = "구매 내역 확인", description = "구매 내역 확인을 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "구매 내역 확인 성공", content = @Content(schema = @Schema(implementation = Purchase.class))),
			@ApiResponse(responseCode = "400", description = "구매 내역 확인 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> getPurchaseHandle(@AuthenticationPrincipal String userId) throws ExistUserException {

		return new ResponseEntity<>(purchaseService.listAll(userId), HttpStatus.OK);
	}
}
