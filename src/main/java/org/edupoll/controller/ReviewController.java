package org.edupoll.controller;

import java.io.IOException;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.exception.NotFoundReviewException;
import org.edupoll.model.dto.request.CreateReviewRequest;
import org.edupoll.model.dto.request.ModifyReviewRequest;
import org.edupoll.model.dto.response.ErrorResponse;
import org.edupoll.model.dto.response.ReviewListResponse;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.model.entity.Review;
import org.edupoll.service.ReviewService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Tag(name = "ReviewController", description = "구매 후기에 관한 컨트롤러입니다.")
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;

	// 구매 후기 작성
	@PostMapping("/{productId}")
	@Operation(summary = "구매 후기 작성", description = "구매 후기 등록을 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "구매 후기 작성 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "구매 후기 작성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> writeReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable Long productId, CreateReviewRequest request)
			throws ExistUserException, IllegalStateException, NotFoundProductException, IOException {

		reviewService.createReview(principal, productId, request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 구매 후기 삭제
	@DeleteMapping("/{reviewId}")
	@Operation(summary = "구매 후기 삭제", description = "구매 후기 삭제를 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "구매 후기 삭제 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "구매 후기 삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> deleteReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable String reviewId) throws NumberFormatException, NotFoundException {

		reviewService.deleteReview(reviewId, principal);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 구매 후기 수정
	@PutMapping("/{reviewId}")
	@Operation(summary = "구매 후기 수정", description = "구매 후기 수정을 위한 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "구매 후기 수정 성공", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "구매 후기 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<?> modifyReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable String reviewId, ModifyReviewRequest request)
			throws NumberFormatException, NotFoundReviewException, AuthenticationException {

		reviewService.modifyReview(principal, reviewId, request);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
