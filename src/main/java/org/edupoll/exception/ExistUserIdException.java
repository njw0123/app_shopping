package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistUserIdException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExistUserIdException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
