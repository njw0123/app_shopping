package org.edupoll.repository;

import java.util.List;
import java.util.Optional;

import org.edupoll.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByProductSubType(String productSubType);

	Optional<Product> findByProductId(String productId);

	List<Product> findByProductSubTypeOrderBySalesRateDesc(String productSubType);

}