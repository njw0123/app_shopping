package org.edupoll.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Datapublic class ModifyReviewRequest {

	private String title; // 리뷰 제목
	private String content; // 리뷰 본문
	private List<MultipartFile> attaches; // 사진

}
