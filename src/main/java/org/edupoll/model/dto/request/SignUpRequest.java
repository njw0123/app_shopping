package org.edupoll.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

	@NotBlank
	private String userId;

	@NotBlank
	private String password;

	@NotBlank
	private String name;
	
	private String address;
	private String phone;

}
