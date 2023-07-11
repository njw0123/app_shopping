package org.edupoll.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String productMainType; // 메인 카테고리
	private String productSubType; // 서브 카테고리
	private String productName; // 상품명
	private String explanation; // 상품에 대한 설명
	private Integer price; // 가격
	private Integer inventory; // 재고
	private Integer salesRate = 0; // 판매량
}
