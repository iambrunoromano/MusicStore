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

import com.musicstore.model.OrderBean;
import com.musicstore.service.DbOrderService;

@RestController
public class OrderRestController {
	
	@Autowired
	private DbOrderService orderService; 
	
	public OrderRestController() {}
	
	@RequestMapping("/musicstore/api/order")
	public Iterable<OrderBean> getAll(){
		return orderService.getAll();
	}
	
	@RequestMapping("/musicstore/api/order/{id}")
	public OrderBean getById(@PathVariable int id){
		Optional<OrderBean> order = orderService.getById(id);
		if(order.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return order.get();
	}

	@RequestMapping(value  ="/musicstore/api/order", method = RequestMethod.POST)
	public OrderBean create(@RequestBody OrderBean p) {
		return orderService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/order/{id}", method = RequestMethod.PUT)
	public OrderBean update(@PathVariable int id, @RequestBody OrderBean p) {
		
		Optional<OrderBean> updatedOrder= orderService.update(id, p);
		if (updatedOrder.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedOrder.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/order/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = orderService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
