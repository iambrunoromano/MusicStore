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

import com.musicstore.model.CartBean;
import com.musicstore.service.DbCartService;

@RestController
public class CartRestController {
	
	@Autowired
	private DbCartService cartService; 
	
	public CartRestController() {}
	
	@RequestMapping("/musicstore/api/cart")
	public Iterable<CartBean> getAll(){
		return cartService.getAll();
	}
	
	@RequestMapping("/musicstore/api/cart/{id}")
	public CartBean getById(@PathVariable int id){
		Optional<CartBean> cart = cartService.getById(id);
		if(cart.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return cart.get();
	}

	@RequestMapping(value  ="/musicstore/api/cart", method = RequestMethod.POST)
	public CartBean create(@RequestBody CartBean p) {
		return cartService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/cart/{id}", method = RequestMethod.PUT)
	public CartBean update(@PathVariable int id, @RequestBody CartBean p) {
		
		Optional<CartBean> updatedCart= cartService.update(id, p);
		if (updatedCart.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCart.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/cart/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = cartService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
