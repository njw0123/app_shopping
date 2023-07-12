package org.edupoll.controller;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.model.dto.request.LoginRequest;
import org.edupoll.model.dto.request.SignUpRequest;
import org.edupoll.model.dto.response.ErrorResponse;
import org.edupoll.model.dto.response.UserResponse;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.model.entity.User;
import org.edupoll.service.JWTService;
import org.edupoll.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Tag(name = "UserController", description = "유저에 관한 컨트롤러입니다.")
public class UserController {

	private final UserService userService;

	private final JWTService jwtService;

	// 회원가입
	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "회원가입처리를 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "회원가입 처리 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "이미 존재하는 ID로 회원가입 처리 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<UserResponse> signUpHandle(SignUpRequest req) throws ExistUserException {

		return new ResponseEntity<>(userService.create(req), HttpStatus.CREATED);
	}

	// 유효성 검사
	@PostMapping("/login")
	@Operation(summary = "유효성 검사", description = "아이디, 비밀번호를 받고 유효성 검사를 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유효성 검사 성공", content = @Content(schema = @Schema(implementation = ValidateUserResponse.class))),
			@ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> loginHandle(LoginRequest req) throws ExistUserException {
		UserResponse userResponse = userService.login(req);
		String token = jwtService.createToken(userResponse.getUserId());

		return new ResponseEntity<>(new ValidateUserResponse(200, token, req.getUserId()), HttpStatus.OK);
	}

	@DeleteMapping("/user/delete")
	@Operation(summary = "회원 탈퇴", description = "아이디, 비밀번호를 받고 회원 탈퇴를 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유효성 검사 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "401", description = "비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 유저", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> userDeleteHandle(@AuthenticationPrincipal String userId, String password)
			throws ExistUserException, InvalidPasswordException {
		userService.delete(userId, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
