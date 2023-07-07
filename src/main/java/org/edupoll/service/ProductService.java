package org.edupoll.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.edupoll.exception.IsAdminException;
import org.edupoll.model.dto.ProductWrapper;
import org.edupoll.model.dto.request.ProductRegistrationRequest;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.ProductAttach;
import org.edupoll.repository.ProductAttachRepository;
import org.edupoll.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductAttachRepository productAttachRepository;

	@Value("${upload.basedir}")
	String baseDir;
	@Value("${upload.server}")
	String uploadServer;

	@Transactional
	public List<ProductWrapper> allItems(int page) {

		List<Product> products = productRepository
				.findAll(PageRequest.of(page - 1, 10, Sort.by(Direction.DESC, "salesRate"))).toList();

		return products.stream().map(e -> new ProductWrapper(e)).toList();
	}

	public Long totalCount() {

		return productRepository.count();
	}

	// 상품등록
	public void create(String userId, ProductRegistrationRequest req) throws IsAdminException {
		if (!userId.equals("admin"))
			throw new IsAdminException("관리자 권한이 아닙니다.");
		
		Product product = new Product();
		if (req.getExplanation() != null)
			product.setExplanation(req.getExplanation());
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

				File uploadDirectory = new File(baseDir + "/product/" + saved.getProductId());
				uploadDirectory.mkdirs();

				String filename = System.currentTimeMillis()
						+ t.getOriginalFilename().substring(t.getOriginalFilename().lastIndexOf("."));

				File dest = new File(uploadDirectory, filename);

				try {
					t.transferTo(dest);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				productAttach.setMediaUrl(uploadServer + "/resource/product/" + saved.getProductId() + "/" + filename);

				return productAttachRepository.save(productAttach);
			}).toList();
		}

	}
}