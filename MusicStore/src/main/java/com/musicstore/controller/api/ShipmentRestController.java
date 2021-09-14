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

import com.musicstore.model.ShipmentBean;
import com.musicstore.service.DbShipmentService;

@RestController
public class ShipmentRestController {
	
	@Autowired
	private DbShipmentService shipmentService; 
	
	public ShipmentRestController() {}
	
	@RequestMapping("/musicstore/api/shipment")
	public Iterable<ShipmentBean> getAll(){
		return shipmentService.getAll();
	}
	
	@RequestMapping("/musicstore/api/shipment/{id}")
	public ShipmentBean getById(@PathVariable int id){
		Optional<ShipmentBean> shipment = shipmentService.getById(id);
		if(shipment.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return shipment.get();
	}

	@RequestMapping(value  ="/musicstore/api/shipment", method = RequestMethod.POST)
	public ShipmentBean create(@RequestBody ShipmentBean p) {
		return shipmentService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/shipment/{id}", method = RequestMethod.PUT)
	public ShipmentBean update(@PathVariable int id, @RequestBody ShipmentBean p) {
		
		Optional<ShipmentBean> updatedShipment= shipmentService.update(id, p);
		if (updatedShipment.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedShipment.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/shipment/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = shipmentService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
