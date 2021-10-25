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

import com.musicstore.model.AdminBean;
import com.musicstore.model.CartBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbCartService;
import com.musicstore.utility.Utility;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;

@RestController
public class CartRestController {

	@Autowired
	private DbAdminService adminService; 
	
	@Autowired
	private DbWebUserService webuserService; 
	
	@Autowired
	private DbCartService cartService; 
	
	public CartRestController() {}
	
	@RequestMapping(value="/musicstore/api/cart", method = RequestMethod.POST)
	public Iterable<CartBean> getAll(@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b))
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return cartService.getAll();
	}
	
	@RequestMapping(value="/musicstore/api/cart/{id}", method = RequestMethod.POST)
	public CartBean getById(@PathVariable int id,@RequestBody WebUserBean b){
		if(!cartService.getById(id).get().getMail().equals(b.getMail())){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return cartService.getById(id).get();
	}

	@RequestMapping(value  ="/musicstore/api/cart", method = RequestMethod.POST)
	public CartBean create(@RequestBody Map<String, Map<String,String>> map) {
		CartBean cb = Utility.cartDeMap(map.get("topost"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!webuserService.isWebUser(b) || !b.getMail().equals(cb.getMail())){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return cartService.create(cb);
	}
	
	@RequestMapping(value  ="/musicstore/api/cart/{id}", method = RequestMethod.PUT)
	public CartBean update(@PathVariable int id,@RequestBody Map<String, Map<String,String>> map) {
		CartBean cb = Utility.cartDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!webuserService.isWebUser(b) || !b.getMail().equals(cb.getMail())){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Optional<CartBean> updatedCart= cartService.update(id, cb);
		if (updatedCart.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCart.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/cart/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
		if(!webuserService.isWebUser(b) || !b.getMail().equals(cartService.getById(id).get().getMail())){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Boolean isDeleted = cartService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
		}
	}

}
