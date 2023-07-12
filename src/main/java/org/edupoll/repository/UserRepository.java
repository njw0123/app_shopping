package org.edupoll.repository;

import java.util.Optional;

import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByUserId(String userId);
	
	Optional<User> findByUserId(String userId);
	
}
