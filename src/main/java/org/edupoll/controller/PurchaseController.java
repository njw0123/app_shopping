package org.edupoll.controller;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartAndPurchaseRequest;
import org.edupoll.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
	private final PurchaseService purchaseService;

	// 구매 내역 생성
	@PostMapping
	public ResponseEntity<?> purchaseCreateHandle(@AuthenticationPrincipal String userId, CartAndPurchaseRequest req)
			throws ExistUserException, NotFoundProductException {
		purchaseService.create(userId, req);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 구매 내역 확인
	@GetMapping
	public ResponseEntity<?> getPurchaseHandle(@AuthenticationPrincipal String userId) throws ExistUserException {

		return new ResponseEntity<>(purchaseService.listAll(userId), HttpStatus.OK);
	}
}
