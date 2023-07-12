package org.edupoll.service;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.model.dto.request.LoginRequest;
import org.edupoll.model.dto.request.SignUpRequest;
import org.edupoll.model.dto.response.UserResponse;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	// 회원가입
	public UserResponse create(SignUpRequest req) throws ExistUserException {
		if (userRepository.existsByUserId(req.getUserId()))
			throw new ExistUserException("이미 존재하는 아이디입니다.");
		
		User user = new User();
		user.setUserId(req.getUserId());
		user.setPassword(req.getPassword());
		user.setName(req.getName());
		user.setAddress(req.getAddress());
		user.setPhone(req.getPhone());
		user.setRole("ROLE_USER");
		return new UserResponse(userRepository.save(user));
	}
	
	// 로그인
	public UserResponse login(LoginRequest req) throws ExistUserException {
		User user = userRepository.findByUserId(req.getUserId()).orElseThrow(() -> new ExistUserException("아이디가 틀렸습니다."));
		if (!user.getPassword().equals(req.getPassword()))
			throw new ExistUserException("비밀번호가 틀렸습니다.");
		
		return new UserResponse(user);
	}
	
	// 회원탈퇴
	public void delete(String userId, String password) throws ExistUserException, InvalidPasswordException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 유저가 존재하지 않습니다."));
		if (!user.getPassword().equals(password))
			throw new InvalidPasswordException("비밀번호가 맞지 않습니다.");
		
		userRepository.delete(user);
	}
}
