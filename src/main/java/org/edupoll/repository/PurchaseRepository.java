package org.edupoll.repository;

import org.edupoll.model.entity.Product;
import java.util.List;
import org.edupoll.model.entity.Purchase;
import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	boolean existsByUserAndProduct(User user, Product product);

	List<Purchase> findByUser(User user);
	
}
