package org.edupoll.model.entity;

import java.util.List;

import org.edupoll.model.dto.request.CreateReviewRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {

	@Id
	private Long id;

	private String writer; // 리뷰 작성자
	private String title; // 리뷰 제목
	private String content; // 리뷰 본문

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

}
