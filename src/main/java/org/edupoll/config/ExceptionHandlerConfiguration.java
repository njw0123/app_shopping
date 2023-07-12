package org.edupoll.config;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.exception.IsAdminException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.exception.NotFoundReviewException;
import org.edupoll.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class ExceptionHandlerConfiguration {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException ex) {
		var response = new ErrorResponse(400, ex.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ JWTDecodeException.class, TokenExpiredException.class })
	public ResponseEntity<ErrorResponse> jwtExceptionHandle(Exception ex) {
		var response = new ErrorResponse(401, "JWT Token is expired or damaged ");

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExistUserException.class)
	public ResponseEntity<ErrorResponse> existUserExceptionHandle(ExistUserException ex) {
		var response = new ErrorResponse(400, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundProductException.class)
	public ResponseEntity<ErrorResponse> notFoundProductExceptionHandle(NotFoundProductException ex) {
		var response = new ErrorResponse(400, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundReviewException.class)
	public ResponseEntity<ErrorResponse> notFoundReviewExceptionHandle(NotFoundReviewException ex) {
		var response = new ErrorResponse(400, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IsAdminException.class)
	public ResponseEntity<ErrorResponse> isAdminExceptionHandle(IsAdminException ex) {
		var response = new ErrorResponse(400, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> invalidPasswordExceptionHandle(InvalidPasswordException ex) {
		var response = new ErrorResponse(401, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}
