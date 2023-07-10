package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IsAdminException extends Exception {

	private static final long serialVersionUID = 1L;

	public IsAdminException(String message) {
		super(message);
	}
	
}
