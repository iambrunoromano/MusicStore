package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Order;
import com.musicstore.entity.Shipment;
import com.musicstore.repository.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Slf4j
public class ShipmentService {

  private final ShipmentRepository shipmentRepository;

  @Autowired
  public ShipmentService(ShipmentRepository shipmentRepository) {
    this.shipmentRepository = shipmentRepository;
  }

  public Iterable<Shipment> getAll() {
    return shipmentRepository.findAll();
  }

  public Optional<Shipment> getById(int id) {
    return shipmentRepository.findById(id);
  }

  public Shipment save(Order order) {
    Shipment shipment =
        Shipment.builder()
            .shipDate(Timestamp.from(Instant.now()))
            .arriveDate(Timestamp.from(Instant.now().plus(7, ChronoUnit.DAYS)))
            .shipAddress(order.getAddress())
            .total(order.getTotal())
            .orderId(order.getId())
            .build();
    shipmentRepository.save(shipment);
    return shipment;
  }

  public void delete(int id) {
    Optional<Shipment> optionalShipment = shipmentRepository.findById(id);
    if (optionalShipment.isPresent()) {
      log.info("Deleting shipment with id [{}]", id);
      shipmentRepository.delete(optionalShipment.get());
    }
    log.warn("Shipment with id [{}] not found", id);
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
  }
}
