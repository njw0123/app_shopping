package org.edupoll.repository;

import org.edupoll.model.entity.ReviewAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAttachRepository extends JpaRepository<ReviewAttach, Long> {

	void deleteByReviewId(Long id);

}
