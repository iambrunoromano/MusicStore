package com.musicstore.controller.api;

import java.util.List;
import java.util.ArrayList; 
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.model.CategoryBean;
import com.musicstore.service.DbCategoryService;

@RestController
public class CategoryRestController {
	
	@Autowired
	private DbCategoryService categoryService; 
	
	public CategoryRestController() {}
	
	@RequestMapping("/musicstore/api/category")
	public Iterable<CategoryBean> getAll(){
		return categoryService.getAll();
	}
	
	@RequestMapping("/musicstore/api/category/{id}")
	public CategoryBean getById(@PathVariable int id){
		Optional<CategoryBean> category = categoryService.getById(id);
		if(category.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return category.get();
	}

	@RequestMapping(value  ="/musicstore/api/category", method = RequestMethod.POST)
	public CategoryBean create(@RequestBody CategoryBean p) {
		return categoryService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/category/{id}", method = RequestMethod.PUT)
	public CategoryBean update(@PathVariable int id, @RequestBody CategoryBean p) {
		
		Optional<CategoryBean> updatedCategory= categoryService.update(id, p);
		if (updatedCategory.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCategory.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/category/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = categoryService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
