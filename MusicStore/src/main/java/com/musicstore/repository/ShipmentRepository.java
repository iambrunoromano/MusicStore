package com.musicstore.repository;

import com.musicstore.entity.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
    List<Shipment> findAll();
}
