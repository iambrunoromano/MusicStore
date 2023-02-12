package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Order;
import com.musicstore.entity.Shipment;
import com.musicstore.service.AdminService;
import com.musicstore.service.OrderService;
import com.musicstore.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "shipment")
public class ShipmentController {

  private final AdminService adminService;
  private final OrderService orderService;
  private final ShipmentService shipmentService;

  @Autowired
  public ShipmentController(
      AdminService adminService, OrderService orderService, ShipmentService shipmentService) {
    this.adminService = adminService;
    this.orderService = orderService;
    this.shipmentService = shipmentService;
  }

  @GetMapping(value = "/all/{admin-id}")
  public ResponseEntity<List<Shipment>> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(shipmentService.getAll());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Shipment> getById(@PathVariable int id, @RequestBody String mail) {
    Optional<Shipment> optionalShipment = shipmentService.getById(id);
    if (!optionalShipment.isPresent()) {
      log.warn("Shipment with id [{}] not found", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
    }
    Shipment shipment = optionalShipment.get();
    orderService.getVerifiedOrder(shipment.getOrderId(), mail);
    return ResponseEntity.ok(shipment);
  }

  @PostMapping(value = "/{admin-id}")
  public ResponseEntity<Shipment> save(@PathVariable String adminId, @RequestBody int orderId) {
    adminService.isAdmin(adminId);
    Order order = orderService.getOrder(orderId);
    return ResponseEntity.ok(shipmentService.save(order));
  }

  @DeleteMapping(value = "/{admin-id}")
  public ResponseEntity<Void> delete(@PathVariable String adminId, @RequestBody int id) {
    adminService.isAdmin(adminId);
    shipmentService.delete(id);
    return ResponseEntity.ok().build();
  }
}
