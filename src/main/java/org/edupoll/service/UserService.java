package org.edupoll.service;

import org.edupoll.exception.ExistUserIdException;
import org.edupoll.model.dto.request.SignUpRequest;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	public void Create(SignUpRequest req) throws ExistUserIdException {
		if (userRepository.existsByUserId(req.getUserId()))
			throw new ExistUserIdException("이미 존재하는 아이디입니다.");
		
		User user = new User();
		user.setUserId(req.getUserId());
		user.setPassword(new BCryptPasswordEncoder().encode(req.getPassword()));
		user.setName(req.getName());
		user.setAddress(req.getAddress());
		user.setPhone(req.getPhone());
		userRepository.save(user);
	}
}
