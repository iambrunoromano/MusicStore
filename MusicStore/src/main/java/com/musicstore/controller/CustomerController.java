package com.musicstore.controller;

import com.musicstore.entity.Customer;
import com.musicstore.request.UserRequest;
import com.musicstore.service.AdminService;
import com.musicstore.service.CustomerService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
  public ResponseEntity<List<Customer>> getAll(@RequestHeader UserRequest userRequest) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(customerService.getAll());
  }

  @GetMapping
  public ResponseEntity<Customer> getById(@RequestHeader UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    Optional<Customer> optionalCustomer = customerService.getById(userRequest.getMail());
    return ResponseEntity.ok(optionalCustomer.get());
  }

  @PostMapping
  public ResponseEntity<Customer> create(
      @RequestHeader UserRequest userRequest, @RequestBody Customer customer) {
    userService.isAuthentic(userRequest);
    customerService.customerIsUser(customer, userRequest);
    return ResponseEntity.ok(customerService.save(customer));
  }

  @DeleteMapping(value = "/{customerId}")
  public ResponseEntity<Void> delete(
      @PathVariable @Email @NotBlank String customerId, @RequestHeader UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    customerService.customerIsUser(Customer.builder().mail(customerId).build(), userRequest);
    customerService.delete(customerId);
    return ResponseEntity.ok().build();
  }
}
