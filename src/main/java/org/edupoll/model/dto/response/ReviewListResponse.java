package org.edupoll.model.dto.response;

import java.util.List;

import org.edupoll.model.entity.Review;

import lombok.Data;

@Data
public class ReviewListResponse {

	private long total;
	private List<Review> reviews;

	public ReviewListResponse(long total, List<Review> reviews) {
		this.reviews = reviews;
		this.total = total;
	}

}
