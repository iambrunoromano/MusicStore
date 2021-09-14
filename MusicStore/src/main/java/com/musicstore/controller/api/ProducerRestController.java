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

import com.musicstore.model.ProducerBean;
import com.musicstore.service.DbProducerService;

@RestController
public class ProducerRestController {
	
	@Autowired
	private DbProducerService producerService; 
	
	public ProducerRestController() {}
	
	@RequestMapping("/musicstore/api/producer")
	public Iterable<ProducerBean> getAll(){
		return producerService.getAll();
	}
	
	@RequestMapping("/musicstore/api/producer/{id}")
	public ProducerBean getById(@PathVariable String id){
		Optional<ProducerBean> producer = producerService.getById(id);
		if(producer.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return producer.get();
	}

	@RequestMapping(value  ="/musicstore/api/producer", method = RequestMethod.POST)
	public ProducerBean create(@RequestBody ProducerBean p) {
		return producerService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/producer/{id}", method = RequestMethod.PUT)
	public ProducerBean update(@PathVariable String id, @RequestBody ProducerBean p) {
		
		Optional<ProducerBean> updatedProducer= producerService.update(id, p);
		if (updatedProducer.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedProducer.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/producer/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		Boolean isDeleted = producerService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
