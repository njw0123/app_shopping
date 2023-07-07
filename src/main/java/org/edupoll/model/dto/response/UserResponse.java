package org.edupoll.model.dto.response;

import org.edupoll.model.entity.User;

import lombok.Data;

@Data
public class UserResponse {
	private Long id;
	private String userId;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String role;
	
	public UserResponse(User user) {
		this.id = user.getId();
		this.userId = user.getUserId();
		this.password = user.getPassword();
		this.name = user.getName();
		this.address = user.getAddress();
		this.phone = user.getPhone();
		this.role = user.getRole();
	}
}
