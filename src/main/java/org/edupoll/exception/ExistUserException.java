package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExistUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
