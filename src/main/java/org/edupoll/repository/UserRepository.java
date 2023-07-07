package org.edupoll.repository;

import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUserId(String userID);
}
