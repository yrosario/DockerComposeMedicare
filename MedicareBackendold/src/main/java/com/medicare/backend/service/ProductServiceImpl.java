package com.medicare.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.backend.model.Product;
import com.medicare.backend.repository.ProductRepository;


@Service
@Transactional
public class ProductServiceImpl implements GenericService<Product, Long> {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product save(Product Product) {
		
		return productRepository.save(Product);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id).isPresent() ? productRepository.findById(id).get() : null;
	}

	@Override
	public Product update(Product Product) {
		return productRepository.findById(Product.getPid()).isPresent() ? productRepository.save(Product) : null;
			
	}

	@Override
	public boolean delete(Long id) {
		if(productRepository.findById(id).isPresent()) {
			productRepository.deleteById(id);
			return true;
		}
		
		return false;
	}

}
