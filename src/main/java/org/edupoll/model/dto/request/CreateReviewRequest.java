package org.edupoll.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateReviewRequest {

	private String title; // 리뷰 제목
	private String description; // 리뷰 본문
	private List<MultipartFile> attaches; // 전달받은 파일들

}
