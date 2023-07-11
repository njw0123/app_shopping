package org.edupoll.controller;

import java.io.IOException;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.exception.NotFoundReviewException;
import org.edupoll.model.dto.request.CreateReviewRequest;
import org.edupoll.model.dto.request.ModifyReviewRequest;
import org.edupoll.model.dto.response.ReviewListResponse;
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

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;

	// 리뷰 목록
	@GetMapping("/{productId}")
	public ResponseEntity<?> getSpecificProductReviewsHandle(@PathVariable Long productId)
			throws NotFoundProductException {
		
		System.out.println(productId);

		long total = reviewService.totalCount(productId);
		List<Review> reviews = reviewService.allItems(productId);
		
		ReviewListResponse response = new ReviewListResponse(total, reviews);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 리뷰 작성
	@PostMapping("/{productId}")
	public ResponseEntity<?> writeReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable Long productId, CreateReviewRequest request)
			throws ExistUserException, IllegalStateException, NotFoundProductException, IOException {

		reviewService.createReview(principal, productId, request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 리뷰 삭제
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable String reviewId) throws NumberFormatException, NotFoundException {

		reviewService.deleteReview(reviewId, principal);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 리뷰 수정
	@PutMapping("/{reviewId}")
	public ResponseEntity<?> modifyReviewHandle(@AuthenticationPrincipal String principal,
			@PathVariable String reviewId, ModifyReviewRequest request)
			throws NumberFormatException, NotFoundReviewException, AuthenticationException {

		reviewService.modifyReview(principal, reviewId, request);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
