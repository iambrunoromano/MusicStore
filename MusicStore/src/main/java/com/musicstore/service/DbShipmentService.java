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

import com.musicstore.repository.IShipmentRepository;
import com.musicstore.model.ShipmentBean;

@Service
public class DbShipmentService {
	
	@Autowired
	private IShipmentRepository ShipmentRepository; 
	
	public Iterable<ShipmentBean> getAll(){
		return ShipmentRepository.findAll();
	}
	
	public Optional<ShipmentBean> getById(int id){
		return ShipmentRepository.findById(id);
	}
	
	public ShipmentBean create(ShipmentBean p) {
		return ShipmentRepository.save(p);
	}
	
	public Optional<ShipmentBean> update(int id,ShipmentBean p) {
			Optional<ShipmentBean> foundShipment = ShipmentRepository.findById(id);
			if(foundShipment.isEmpty()) {
				return Optional.empty();
			}
			
			foundShipment.get().setShipDate(p.getShipDate());
			foundShipment.get().setArriveDate(p.getArriveDate());
			foundShipment.get().setShipAddress(p.getShipAddress());
			foundShipment.get().setTotal(p.getTotal());
			
			ShipmentRepository.save(foundShipment.get());
			return foundShipment;
		}

	public boolean delete(int id) {
		Optional<ShipmentBean> foundShipment = ShipmentRepository.findById(id);
		if(foundShipment.isEmpty()) {
			return false;
		}
		ShipmentRepository.delete(foundShipment.get());
		return false;
	}
}
