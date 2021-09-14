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

import com.musicstore.repository.IOrderRepository;
import com.musicstore.model.OrderBean;

@Service
public class DbOrderService {
	
	@Autowired
	private IOrderRepository OrderRepository; 
	
	public Iterable<OrderBean> getAll(){
		return OrderRepository.findAll();
	}
	
	public Optional<OrderBean> getById(int id){
		return OrderRepository.findById(id);
	}
	
	public OrderBean create(OrderBean p) {
		return OrderRepository.save(p);
	}
	
	public Optional<OrderBean> update(int id,OrderBean p) {
			Optional<OrderBean> foundOrder = OrderRepository.findById(id);
			if(foundOrder.isEmpty()) {
				return Optional.empty();
			}
			
			foundOrder.get().setDate(p.getDate());
			foundOrder.get().setTotal(p.getTotal());
			
			OrderRepository.save(foundOrder.get());
			return foundOrder;
		}

	public boolean delete(int id) {
		Optional<OrderBean> foundOrder = OrderRepository.findById(id);
		if(foundOrder.isEmpty()) {
			return false;
		}
		OrderRepository.delete(foundOrder.get());
		return false;
	}
}
