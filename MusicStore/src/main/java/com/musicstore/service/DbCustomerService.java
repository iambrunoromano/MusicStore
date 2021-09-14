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

import com.musicstore.repository.ICustomerRepository;
import com.musicstore.model.CustomerBean;

@Service
public class DbCustomerService {
	
	@Autowired
	private ICustomerRepository CustomerRepository; 
	
	public Iterable<CustomerBean> getAll(){
		return CustomerRepository.findAll();
	}
	
	public Optional<CustomerBean> getById(int id){
		return CustomerRepository.findById(id);
	}
	
	public CustomerBean create(CustomerBean p) {
		return CustomerRepository.save(p);
	}
	
	public Optional<CustomerBean> update(int id,CustomerBean p) {
			Optional<CustomerBean> foundCustomer = CustomerRepository.findById(id);
			if(foundCustomer.isEmpty()) {
				return Optional.empty();
			}
			
			foundCustomer.get().setName(p.getName());
			foundCustomer.get().setSurname(p.getSurname());
			foundCustomer.get().setAddress(p.getAddress());
			foundCustomer.get().setPaymentCard(p.getPaymentCard());
			foundCustomer.get().setBillingAddress(p.getBillingAddress());
			
			CustomerRepository.save(foundCustomer.get());
			return foundCustomer;
		}

	public boolean delete(int id) {
		Optional<CustomerBean> foundCustomer = CustomerRepository.findById(id);
		if(foundCustomer.isEmpty()) {
			return false;
		}
		CustomerRepository.delete(foundCustomer.get());
		return false;
	}
}
