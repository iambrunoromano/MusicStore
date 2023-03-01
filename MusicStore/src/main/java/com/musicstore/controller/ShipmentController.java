package com.musicstore.controller;

import com.musicstore.entity.Order;
import com.musicstore.entity.Shipment;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.OrderService;
import com.musicstore.service.ShipmentService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "shipments")
public class ShipmentController {

  private final AdminService adminService;
  private final OrderService orderService;
  private final ShipmentService shipmentService;
  private final UserService userService;

  @Autowired
  public ShipmentController(
      AdminService adminService,
      OrderService orderService,
      ShipmentService shipmentService,
      UserService userService) {
    this.adminService = adminService;
    this.orderService = orderService;
    this.shipmentService = shipmentService;
    this.userService = userService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Shipment>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(shipmentService.getAll());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Shipment> getById(@PathVariable int id, @RequestHeader User user) {
    userService.isAuthentic(user);
    Shipment shipment = shipmentService.getById(id);
    orderService.getVerifiedOrder(shipment.getOrderId(), user.getMail());
    return ResponseEntity.ok(shipment);
  }

  @PostMapping(value = "/{order-id}")
  public ResponseEntity<Shipment> save(@RequestHeader User user, @PathVariable int orderId) {
    adminService.isAdmin(user);
    Order order = orderService.getOrder(orderId);
    return ResponseEntity.ok(shipmentService.save(order));
  }

  @DeleteMapping(value = "/{order-id}")
  public ResponseEntity<Void> delete(@RequestHeader User user, @PathVariable int orderId) {
    adminService.isAdmin(user);
    shipmentService.delete(orderId);
    return ResponseEntity.ok().build();
  }
}
