package org.edupoll.repository;

import java.util.List;
import java.util.Optional;

import org.edupoll.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByProductSubTypeAndProductMainTypeOrderBySalesRateDesc(String productSubType, String productMainType);

	int countByProductSubTypeAndProductMainTypeOrderBySalesRateDesc(String productSubType, String productMainType);
}
