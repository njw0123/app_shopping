package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Long countByProduct(Product found);

	List<Review> findByProduct(Product found);

}
