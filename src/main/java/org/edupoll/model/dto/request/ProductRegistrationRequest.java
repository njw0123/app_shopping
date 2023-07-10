package org.edupoll.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRegistrationRequest {
	private String productName; // 상품명
	private String explanation; // 설명
	private List<MultipartFile> attaches; // 사진
	private String productMainType; // 메인카테고리
	private String productSubType; // 서브카테고리
	private Integer price; // 가격
	private Integer inventory; // 재고량
}
