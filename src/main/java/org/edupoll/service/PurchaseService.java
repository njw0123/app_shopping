package org.edupoll.service;

import java.util.List;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartAndPurchaseRequest;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.Purchase;
import org.edupoll.model.entity.User;
import org.edupoll.repository.CartRepository;
import org.edupoll.repository.ProductRepository;
import org.edupoll.repository.PurchaseRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Transactional
	public void create(String userId, CartAndPurchaseRequest req) throws ExistUserException, NotFoundProductException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 아이디를 찾지 못했습니다."));

		Product product = productRepository.findById(req.getProductId())
				.orElseThrow(() -> new NotFoundProductException("해당 물품이 존재하지 않습니다."));

		if (!cartRepository.existsByUserAndProduct(user, product))
			throw new NotFoundProductException("해당 물품이 장바구니에 존재하지 않습니다.");

		if (product.getInventory() <= 0)
			throw new NotFoundProductException("해당 물품의 재고가 없습니다.");

		Purchase purchase = new Purchase();
		purchase.setProduct(product);
		purchase.setUser(user);
		purchase.setQuantity(req.getQuantity());
		purchaseRepository.save(purchase);

		cartRepository.deleteByUserIdAndProduct(user.getId(), product);
	}

	public List<Purchase> listAll(String userId) throws ExistUserException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 아이디를 찾지 못했습니다."));

		return purchaseRepository.findByUser(user);
	}
}
