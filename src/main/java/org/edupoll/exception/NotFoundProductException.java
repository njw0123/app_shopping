package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundProductException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundProductException(String message) {
		super(message);
	}

}
