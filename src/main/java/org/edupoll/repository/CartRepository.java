package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Cart;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
	List<Cart> findByUserId(Long id);
	
	boolean existsByUserAndProduct(User user, Product product);
	
	void deleteByUserIdAndProduct(Long userId, Product product);
}
