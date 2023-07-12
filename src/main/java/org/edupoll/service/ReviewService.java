package org.edupoll.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.exception.NotFoundReviewException;
import org.edupoll.model.dto.request.CreateReviewRequest;
import org.edupoll.model.dto.request.ModifyReviewRequest;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.ProductAttach;
import org.edupoll.model.entity.Review;
import org.edupoll.model.entity.ReviewAttach;
import org.edupoll.repository.ProductRepository;
import org.edupoll.repository.ReviewAttachRepository;
import org.edupoll.repository.ReviewRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewAttachRepository reviewAttachRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Value("${upload.basedir}")
	private String uploadBaseDir;

	@Value("${upload.server}")
	private String uploadServer;

	// 리뷰 작성
	@Transactional
	public void createReview(String principal, Long productId, CreateReviewRequest request)
			throws ExistUserException, NotFoundProductException, IllegalStateException, IOException {

		var user = userRepository.findByUserId(principal)
				.orElseThrow(() -> new ExistUserException("리뷰작성시 회원가입이 필요합니다."));

		var product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundProductException("해당 상품 ID는 존재하지 않는 ID입니다."));

		Review review = new Review();

		review.setProduct(product);
		review.setWriter(user.getUserId());
		review.setTitle(request.getTitle());
		review.setContent(request.getContent());

		var saved = reviewRepository.save(review);
		log.info("attaches is exist ? {}", request.getAttaches() != null);

		if (request.getAttaches() != null) { // 파일이 넘어왔다면
			File uploadDirectory = new File(uploadBaseDir + "/review/" + saved.getId());
			uploadDirectory.mkdirs();

			for (MultipartFile multi : request.getAttaches()) { // 하나씩 반복문 돌면서
				// 어디다가 file 옮겨둘껀지 File 객체로 정의하고
				String fileName = String.valueOf(System.currentTimeMillis());
				String extension = multi.getOriginalFilename().split("\\.")[1];
				File dest = new File(uploadDirectory, fileName + "." + extension);

				multi.transferTo(dest); // 옮기는걸 진행

				// 업로드가 끝나면 DB에 기록
				ReviewAttach reviewAttach = new ReviewAttach();
				reviewAttach.setType(multi.getContentType());
				// 업로드를 한 곳이 어디냐에 따라서 결정이 되는 값
				reviewAttach.setMediaUrl(
						uploadServer + "/resource/review/" + saved.getId() + "/" + fileName + "." + extension);
				reviewAttach.setReview(saved);
				reviewAttachRepository.save(reviewAttach);
			}
		}
	}

	// 리뷰 삭제
	@Transactional
	public boolean deleteReview(String reviewId, String principal) throws NumberFormatException, NotFoundException {
		Review found = reviewRepository.findById(Long.parseLong(reviewId)).orElseThrow(() -> new NotFoundException());

		if (found.getWriter().equals(principal)) {
			reviewAttachRepository.deleteByReviewId(found.getId());
			reviewRepository.delete(found);

			return true;
		}
		throw new RuntimeException("리뷰 삭제는 작성자만 가능합니다.");
	}

	// 리뷰 수정
	@Transactional
	public void modifyReview(String principal, String reviewId, ModifyReviewRequest request)
			throws NumberFormatException, NotFoundReviewException, AuthenticationException {

		Review found = reviewRepository.findById(Long.parseLong(reviewId))
				.orElseThrow(() -> new NotFoundReviewException("해당 ID에 해당하는 리뷰가 존재하지 않습니다."));

		List<ReviewAttach> attaches = reviewAttachRepository.findByReviewId(reviewId);

		if (!found.getWriter().equals(principal)) {
			throw new AuthenticationException("수정은 작성자만 가능합니다.");
		}

		found.setContent(request.getContent());
		found.setTitle(request.getTitle());

		if (request.getAttaches() != null) {
			List<ReviewAttach> multipartFiles = request.getAttaches().stream().map(t -> {
				ReviewAttach reviewAttach = new ReviewAttach();
				reviewAttach.setReview(found);
				reviewAttach.setType(t.getContentType());

				File uploadDirectory = new File(uploadBaseDir + "/review/" + found.getId());
				uploadDirectory.mkdirs();

				String filename = System.currentTimeMillis()
						+ t.getOriginalFilename().substring(t.getOriginalFilename().lastIndexOf("."));

				File dest = new File(uploadDirectory, filename);

				try {
					t.transferTo(dest);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				reviewAttach.setMediaUrl(uploadServer + "/resource/review/" + found.getId() + "/" + filename);

				return reviewAttachRepository.save(reviewAttach);
			}).toList();
		}

		reviewRepository.save(found);
	}

}
