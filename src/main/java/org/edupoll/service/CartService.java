package org.edupoll.service;

import java.util.List;

import org.edupoll.exception.ExistUserException;
import org.edupoll.exception.NotFoundProductException;
import org.edupoll.model.dto.request.CartAndPurchaseRequest;
import org.edupoll.model.entity.Cart;
import org.edupoll.model.entity.Product;
import org.edupoll.model.entity.User;
import org.edupoll.repository.CartRepository;
import org.edupoll.repository.ProductRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	public void create(String userId, CartCreateRequest req) throws NotFoundProductException, ExistUserException {
	
	@Transactional
	public void create(String userId, CartAndPurchaseRequest req) throws NotFoundProductException, ExistUserException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 아이디를 찾지 못했습니다."));
		
		Product product = productRepository.findById(req.getProductId())
				.orElseThrow(() -> new NotFoundProductException("해당 물품을 찾지 못했습니다."));
		
		if (cartRepository.existsByUserAndProduct(user, product)) {
			cartRepository.deleteByUserIdAndProduct(user.getId(), product);
		}

		Cart cart = new Cart();
		cart.setProduct(product);
		cart.setUser(user);
		cart.setQuantity(req.getQuantity());
		
		cartRepository.save(cart);
	}

	@Transactional
	public List<Cart> allList(String userId) throws ExistUserException{
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 아이디가 존재하지 않습니다."));
		
		return cartRepository.findByUserId(user.getId());
	}
	
	@Transactional
	public void delete(String userId, Long productId) throws ExistUserException, NotFoundProductException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new ExistUserException("해당 아이디가 존재하지 않습니다."));
		Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException("해당 물품을 찾지 못했습니다."));
		if (!cartRepository.existsByUserAndProduct(user, product))
			throw new NotFoundProductException("장바구니에 존재하지 않습니다.");
		
		cartRepository.deleteByUserIdAndProduct(user.getId(), product);
	}
}
