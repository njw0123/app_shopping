package org.edupoll.model.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	private String productMainType;
	private String productSubType;

	private String productName;

	private String explanation;
	
	private Integer price;

	private Integer inventory;

	private Integer salesRate = 0;
	
	@OneToMany(mappedBy = "product")
	private List<ProductAttach> attachs;

}
