package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
	List<Cart> findByUserId(Long id);
}
