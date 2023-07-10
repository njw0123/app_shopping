package org.edupoll.controller;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartCreateRequest;
import org.edupoll.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	
	@PostMapping("/cart")
	public ResponseEntity<?> createCartHandle(@AuthenticationPrincipal String userId, CartCreateRequest req) throws NotFoundProductException, ExistUserException {
		cartService.create(userId, req);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping("/cart")
	public ResponseEntity<?> getCartList(@AuthenticationPrincipal String userId) throws ExistUserException {	
		return new ResponseEntity<>(cartService.allList(userId), HttpStatus.OK);
	}
}
