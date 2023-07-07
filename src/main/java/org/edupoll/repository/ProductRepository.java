package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByProductMainType(String productMainType);

}
