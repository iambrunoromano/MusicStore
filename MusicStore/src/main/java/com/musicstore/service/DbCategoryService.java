package com.musicstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.musicstore.repository.ICategoryRepository;
import com.musicstore.model.CategoryBean;

@Service
public class DbCategoryService {
	
	@Autowired
	private ICategoryRepository CategoryRepository; 
	
	public Iterable<CategoryBean> getAll(){
		return CategoryRepository.findAll();
	}
	
	public Optional<CategoryBean> getById(int id){
		return CategoryRepository.findById(id);
	}
	
	public CategoryBean create(CategoryBean p) {
		return CategoryRepository.save(p);
	}
	
	public Optional<CategoryBean> update(int id,CategoryBean p) {
			Optional<CategoryBean> foundCategory = CategoryRepository.findById(id);
			if(foundCategory.isEmpty()) {
				return Optional.empty();
			}
			
			foundCategory.get().setName(p.getName());
			foundCategory.get().setParent(p.getParent());
			
			CategoryRepository.save(foundCategory.get());
			return foundCategory;
		}

	public boolean delete(int id) {
		Optional<CategoryBean> foundCategory = CategoryRepository.findById(id);
		if(foundCategory.isEmpty()) {
			return false;
		}
		CategoryRepository.delete(foundCategory.get());
		return false;
	}
}
