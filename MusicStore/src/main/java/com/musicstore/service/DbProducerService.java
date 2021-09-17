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

import com.musicstore.repository.IProducerRepository;
import com.musicstore.model.AdminBean;
import com.musicstore.model.ProducerBean;
import com.musicstore.model.WebUserBean;

@Service
public class DbProducerService {
	
	@Autowired
	private IProducerRepository ProducerRepository; 
	
	public Iterable<ProducerBean> getAll(){
		return ProducerRepository.findAll();
	}
	
	public Optional<ProducerBean> getById(String id){
		return ProducerRepository.findById(id);
	}
	
	public ProducerBean create(ProducerBean p) {
		return ProducerRepository.save(p);
	}
	
	public Optional<ProducerBean> update(String id,ProducerBean p) {
			Optional<ProducerBean> foundProducer = ProducerRepository.findById(id);
			if(foundProducer.isEmpty()) {
				return Optional.empty();
			}
			
			foundProducer.get().setName(p.getName());
			foundProducer.get().setAddress(p.getAddress());
			
			ProducerRepository.save(foundProducer.get());
			return foundProducer;
		}

	public boolean delete(String id) {
		Optional<ProducerBean> foundProducer = ProducerRepository.findById(id);
		if(foundProducer.isEmpty()) {
			return false;
		}
		ProducerRepository.delete(foundProducer.get());
		return false;
	}
	
	public boolean isProducer(WebUserBean wub) {
		Optional<ProducerBean> producerFound = this.getById(wub.getMail());
		if(!producerFound.isEmpty())
			if(producerFound.get().getMail().equals(wub.getMail()))
				return true;
		return false;
	}
}
