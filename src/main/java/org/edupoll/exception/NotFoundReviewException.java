package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundReviewException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundReviewException(String message) {
		super(message);
	}

}
