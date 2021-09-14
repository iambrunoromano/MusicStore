package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.ShipmentBean;

@Repository
public interface IShipmentRepository extends CrudRepository<ShipmentBean, Integer>{
}
