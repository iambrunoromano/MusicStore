package com.musicstore.controller;

import com.musicstore.entity.Customer;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CustomerService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "customers")
public class CustomerController {

  private final AdminService adminService;
  private final UserService userService;
  private final CustomerService customerService;

  @Autowired
  public CustomerController(
      AdminService adminService, UserService userService, CustomerService customerService) {
    this.adminService = adminService;
    this.userService = userService;
    this.customerService = customerService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Customer>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(customerService.getAll());
  }

  @GetMapping
  public ResponseEntity<Customer> getById(@RequestHeader User user) {
    userService.isAuthentic(user);
    Optional<Customer> optionalCustomer = customerService.getById(user.getMail());
    return ResponseEntity.ok(optionalCustomer.get());
  }

  @PostMapping
  public ResponseEntity<Customer> create(@RequestHeader User user, @RequestBody Customer customer) {
    userService.isAuthentic(user);
    customerService.customerIsUser(customer, user);
    return ResponseEntity.ok(customerService.save(customer));
  }

  @DeleteMapping(value = "/{customer-id}")
  public ResponseEntity<Void> delete(@PathVariable String customerId, @RequestHeader User user) {
    userService.isAuthentic(user);
    customerService.customerIsUser(Customer.builder().mail(customerId).build(), user);
    customerService.delete(customerId);
    return ResponseEntity.ok().build();
  }
}
