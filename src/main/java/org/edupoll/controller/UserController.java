package org.edupoll.controller;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.model.dto.request.LoginRequest;
import org.edupoll.model.dto.request.SignUpRequest;
import org.edupoll.model.dto.response.UserResponse;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.service.JWTService;
import org.edupoll.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final JWTService jwtService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signUpHandle(SignUpRequest req) throws ExistUserException {

		return new ResponseEntity<>(userService.create(req), HttpStatus.CREATED);
	}

	// 로그인(유효성 검사)
	@PostMapping("/login")
	public ResponseEntity<?> loginHandle(LoginRequest req) throws ExistUserException {
		UserResponse userResponse = userService.login(req);
		String token = jwtService.createToken(userResponse.getUserId());

		return new ResponseEntity<>(new ValidateUserResponse(200, token, req.getUserId()), HttpStatus.OK);
	}
	
	@DeleteMapping("/user/delete")
	public ResponseEntity<?> userDeleteHandle(@AuthenticationPrincipal String userId, String password) throws ExistUserException, InvalidPasswordException {
		userService.delete(userId, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
