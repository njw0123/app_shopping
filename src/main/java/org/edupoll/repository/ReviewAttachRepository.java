package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.ReviewAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAttachRepository extends JpaRepository<ReviewAttach, Long> {

	void deleteByReviewId(Long id);

	List<ReviewAttach> findByReviewId(String reviewId);

}
