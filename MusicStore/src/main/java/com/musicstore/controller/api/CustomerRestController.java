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
import com.musicstore.model.CustomerBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbCustomerService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;

@RestController
public class CustomerRestController {

	@Autowired
	private DbAdminService adminService; 
	
	@Autowired
	private DbWebUserService webuserService; 
	
	@Autowired
	private DbCustomerService customerService; 
	
	public CustomerRestController() {}
	
	@RequestMapping(value="/musicstore/api/customer/all", method = RequestMethod.POST)
	public Iterable<CustomerBean> getAll(@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b))
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return customerService.getAll();
	}
	
	@RequestMapping(value="/musicstore/api/customer/{id}", method = RequestMethod.POST)
	public CustomerBean getById(@PathVariable String id,@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b) && !(webuserService.isWebUser(b) && b.getMail().equals(id))){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return customerService.getById(id).get();
	}

	@RequestMapping(value="/musicstore/api/customer", method = RequestMethod.POST)
	public CustomerBean create(@RequestBody CustomerBean p) {
		return customerService.create(p);
	}
	
	@RequestMapping(value="/musicstore/api/customer/{id}", method = RequestMethod.PUT)
	public CustomerBean update(@PathVariable String id,@RequestBody Map<String, Map<String,String>> map) {
		CustomerBean cb = Utility.customerDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			if(!webuserService.isWebUser(b) || !b.getMail().equals(cb.getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized user");
			}
		}
		Optional<CustomerBean> updatedCustomer= customerService.update(id, cb);
		if (updatedCustomer.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCustomer.get();
	}
	
	@RequestMapping(value="/musicstore/api/customer/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)){
			if(!webuserService.isWebUser(b) || !b.getMail().equals(customerService.getById(id).get().getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
			}
		}
		Boolean isDeleted = customerService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
