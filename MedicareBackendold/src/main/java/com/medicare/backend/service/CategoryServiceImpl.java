package com.medicare.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.backend.model.Category;
import com.medicare.backend.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements GenericService<Category, Long> {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category save(Category category) {
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id).isPresent() ? categoryRepository.findById(id).get() : null;
	}

	@Override
	public Category update(Category category) {
		return categoryRepository.findById(category.getId()).isPresent() ? categoryRepository.save(category) : null;
			
	}

	@Override
	public boolean delete(Long id) {
		if(categoryRepository.findById(id).isPresent()) {
			categoryRepository.deleteById(id);
			return true;
		}
		
		return false;
	}

}
