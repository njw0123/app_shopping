package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.ProductAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttachRepository extends JpaRepository<ProductAttach, Long> {
	List<ProductAttach> findByProductId(Long id);
}
