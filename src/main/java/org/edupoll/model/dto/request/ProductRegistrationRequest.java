package org.edupoll.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRegistrationRequest {

	private String productName;
	private String explanation;
	private List<MultipartFile> attaches;
	private String productMainType;
	private String productSubType;
	private Integer price;
	private Integer inventory;

}
