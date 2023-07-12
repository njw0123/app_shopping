package org.edupoll.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.edupoll.exception.IsAdminException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.dto.request.ProductRegistrationRequest;
import org.edupoll.model.dto.response.ProductListResponse;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.ProductAttach;
import org.edupoll.model.entity.Review;
import org.edupoll.repository.ProductAttachRepository;
import org.edupoll.repository.ProductRepository;
import org.edupoll.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final ProductAttachRepository productAttachRepository;

	@Value("${upload.basedir}")
	String baseDir;
	
	@Value("${upload.server}")
	String uploadServer;

	// 카테고리를 통한 상품 조회
	@Transactional
	public ProductListResponse allItems(String productMainType, String productSubType, int page) throws NotFoundProductException {
		
		List<Product> list = productRepository
				.findByProductSubTypeAndProductMainTypeOrderBySalesRateDesc(productSubType, productMainType);
		if(list.size() == 0) {
			throw new NotFoundProductException();
		}
		List<ProductWrapper> productWrappers = list.stream().map(t -> new ProductWrapper(t)).toList();
		productWrappers.stream().forEach(t -> {
			t.setProductAttachs(productAttachRepository.findByProductId(t.getId()));
		});
		
		return new ProductListResponse(productRepository.countByProductSubTypeAndProductMainTypeOrderBySalesRateDesc(productSubType, productMainType), productWrappers);
	}

	// 상품 ID를 통한 상품 조회
	public ProductWrapper getSpecificProduct(Long productId) throws NotFoundProductException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundProductException("존재하지 않은 상품 ID 입니다."));
		
		List<Review> reviews =reviewRepository.findByProduct(product);

		return new ProductWrapper(product, reviews);
	}

	// 상품등록
	public void create(String userId, ProductRegistrationRequest req) throws IsAdminException {
		
		if (!userId.equals("admin"))
			throw new IsAdminException("관리자 권한이 아닙니다.");

		Product product = new Product();
		if (req.getExplanation() != null) {
			product.setExplanation(req.getExplanation());
		}

		product.setProductName(req.getProductName());
		product.setProductMainType(req.getProductMainType());
		product.setProductSubType(req.getProductSubType());
		product.setPrice(req.getPrice());
		product.setInventory(req.getInventory());
		Product saved = productRepository.save(product);

		if (req.getAttaches() != null) {
			List<ProductAttach> multipartFiles = req.getAttaches().stream().map(t -> {
				ProductAttach productAttach = new ProductAttach();
				productAttach.setProduct(saved);
				productAttach.setType(t.getContentType());

				File uploadDirectory = new File(baseDir + "/product/" + saved.getId());
				uploadDirectory.mkdirs();

				String filename = System.currentTimeMillis()
						+ t.getOriginalFilename().substring(t.getOriginalFilename().lastIndexOf("."));

				File dest = new File(uploadDirectory, filename);

				try {
					t.transferTo(dest);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				productAttach.setMediaUrl(uploadServer + "/attach/product/" + saved.getId() + "/" + filename);

				return productAttachRepository.save(productAttach);
			}).toList();
		}
	}
	
	// 상품 검색
	public ProductListResponse search(String query) throws NotFoundProductException {
		List<Product> products = productRepository.findByProductNameContainingOrderBySalesRateDesc(query);
		if (products.size() == 0)
			throw new NotFoundProductException("검색결과가 없습니다.");
		
		List<ProductWrapper> productWrappers = products.stream().map(t -> new ProductWrapper(t)).toList();
		productWrappers.stream().forEach(t -> {
			t.setProductAttachs(productAttachRepository.findByProductId(t.getId()));
		});
		
		return new ProductListResponse(productRepository.countByProductNameContainingOrderBySalesRateDesc(query), productWrappers);
	}
	
	// 상품 삭제
	@Transactional
	public void delete(String userId, Long productId) throws IsAdminException, NotFoundProductException {
		if (!userId.equals("admin"))
			throw new IsAdminException("관리자 권한이 아닙니다.");
		
		Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException("해당 물품을 찾지 못했습니다."));
		productAttachRepository.deleteByProductId(productId);
		productRepository.delete(product);
	}
}
