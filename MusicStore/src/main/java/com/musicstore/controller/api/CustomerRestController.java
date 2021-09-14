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

import com.musicstore.model.CustomerBean;
import com.musicstore.service.DbCustomerService;

@RestController
public class CustomerRestController {
	
	@Autowired
	private DbCustomerService customerService; 
	
	public CustomerRestController() {}
	
	@RequestMapping("/musicstore/api/customer")
	public Iterable<CustomerBean> getAll(){
		return customerService.getAll();
	}
	
	@RequestMapping("/musicstore/api/customer/{id}")
	public CustomerBean getById(@PathVariable int id){
		Optional<CustomerBean> customer = customerService.getById(id);
		if(customer.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return customer.get();
	}

	@RequestMapping(value  ="/musicstore/api/customer", method = RequestMethod.POST)
	public CustomerBean create(@RequestBody CustomerBean p) {
		return customerService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/customer/{id}", method = RequestMethod.PUT)
	public CustomerBean update(@PathVariable int id, @RequestBody CustomerBean p) {
		
		Optional<CustomerBean> updatedCustomer= customerService.update(id, p);
		if (updatedCustomer.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedCustomer.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/customer/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = customerService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
