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

import com.musicstore.model.ProductBean;
import com.musicstore.service.DbProductService;

@RestController
public class ProductRestController {
	
	@Autowired
	private DbProductService productService; 
	
	public ProductRestController() {}
	
	@RequestMapping("/musicstore/api/product")
	public Iterable<ProductBean> getAll(){
		return productService.getAll();
	}
	
	@RequestMapping("/musicstore/api/product/{id}")
	public ProductBean getById(@PathVariable int id){
		Optional<ProductBean> product = productService.getById(id);
		if(product.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return product.get();
	}

	@RequestMapping(value  ="/musicstore/api/product", method = RequestMethod.POST)
	public ProductBean create(@RequestBody ProductBean p) {
		return productService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.PUT)
	public ProductBean update(@PathVariable int id, @RequestBody ProductBean p) {
		
		Optional<ProductBean> updatedProduct= productService.update(id, p);
		if (updatedProduct.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedProduct.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = productService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
