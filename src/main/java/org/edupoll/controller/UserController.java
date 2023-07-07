package org.edupoll.controller;

import org.edupoll.exception.ExistUserIdException;
import org.edupoll.model.dto.request.SignUpRequest;
import org.edupoll.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
	public ResponseEntity<?> SignUpView() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> SignUpHandle(SignUpRequest req) throws ExistUserIdException {
		userService.Create(req);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginView() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginHandle() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
