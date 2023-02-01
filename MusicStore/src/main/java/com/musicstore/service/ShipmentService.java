package com.musicstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Shipment;

@Service
public class DbShipmentService {
	
	@Autowired
	private com.musicstore.repository.ShipmentRepository ShipmentRepository;
	
	public Iterable<Shipment> getAll(){
		return ShipmentRepository.findAll();
	}
	
	public Optional<Shipment> getById(int id){
		return ShipmentRepository.findById(id);
	}
	
	public Shipment create(Shipment p) {
		return ShipmentRepository.save(p);
	}
	
	public Optional<Shipment> update(int id, Shipment p) {
			Optional<Shipment> foundShipment = ShipmentRepository.findById(id);
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
		Optional<Shipment> foundShipment = ShipmentRepository.findById(id);
		if(foundShipment.isEmpty()) {
			return false;
		}
		ShipmentRepository.delete(foundShipment.get());
		return false;
	}
}
