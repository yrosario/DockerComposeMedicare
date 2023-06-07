package com.medicare.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.backend.model.Cart;
import com.medicare.backend.repository.CartRepository;

@Service
public class CartServiceImpl implements GenericService<Cart, Long> {
	
	@Autowired
	private CartRepository cartRepository;
	

	@Override
	public Cart save(Cart cart) {
		return this.cartRepository.save(cart);
	}

	@Override
	public List<Cart> findAll() {
		return this.cartRepository.findAll();
	}

	@Override
	public Cart findById(Long id) {
		Optional<Cart> oCart = cartRepository.findById(id);
		return oCart.isPresent() ? oCart.get() : null;
	}

	@Override
	public Cart update(Cart cart) {
		return cartRepository.save(cart);
	}

	@Override
	public boolean delete(Long id) {
		if(cartRepository.findById(id).isPresent()) {
			cartRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
