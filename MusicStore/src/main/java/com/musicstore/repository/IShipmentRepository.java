package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.ShipmentBean;

public interface IShipmentRepository extends CrudRepository<ShipmentBean, Integer>{
}
