package com.musicstore.controller.api;

import java.util.List;
import java.util.Map;
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

import com.musicstore.model.CartBean;
import com.musicstore.model.CategoryBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbCategoryService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;

@RestController
public class CategoryRestController {

	@Autowired
	private DbAdminService adminService; 
	
	@Autowired
	private DbWebUserService webuserService;
	
	@Autowired
	private DbCategoryService categoryService; 
	
	public CategoryRestController() {}
	
	@RequestMapping(value="/musicstore/api/category/all", method = RequestMethod.POST)
	public Iterable<CategoryBean> getAll(){
		return categoryService.getAll();
	}
	
	@RequestMapping(value="/musicstore/api/category/{id}", method = RequestMethod.POST)
	public CategoryBean getById(@PathVariable int id){
		/*EVERYONE SHOULD BE ABLE TO GET INFOS ABOUT SPECIFIC CATEGORY*/
		Optional<CategoryBean> category = categoryService.getById(id);
		if(category.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return category.get();
	}

	@RequestMapping(value  ="/musicstore/api/category", method = RequestMethod.POST)
	public CategoryBean create(@RequestBody Map<String, Map<String,String>> map) {
		CategoryBean cb = Utility.categoryDeMap(map.get("topost"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return categoryService.create(cb);
	}
	
	@RequestMapping(value  ="/musicstore/api/category/{id}", method = RequestMethod.PUT)
	public CategoryBean update(@PathVariable int id,@RequestBody Map<String, Map<String,String>> map) {
		CategoryBean cb = Utility.categoryDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Optional<CategoryBean> updatedCategory= categoryService.update(id, cb);
		if (updatedCategory.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCategory.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/category/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Boolean isDeleted = categoryService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
	}

}
