package com.musicstore.controller;

import com.musicstore.entity.Customer;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CustomerService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: external test
// TODO: Returned Response Entity HTTP Status
// TODO: understand how the customer route differs in the usage from user route

@RestController
@Slf4j
@RequestMapping(value = "customer")
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
  public List<Customer> getAll(@RequestBody User user) {
    adminService.isAdmin(user.getMail());
    return customerService.getAll();
  }

  @GetMapping
  public Customer getById(@RequestBody User user) {
    userService.isAuthentic(user);
    Optional<Customer> optionalCustomer = customerService.getById(user.getMail());
    return optionalCustomer.get();
  }

  @PostMapping
  public Customer create(@RequestBody User user, @RequestBody Customer customer) {
    // TODO: needed here to verify that the user is posting the his own customer by asserting
    // user.mail = customer.mail
    userService.isAuthentic(user);
    return customerService.save(customer);
  }

  @DeleteMapping(value = "/{customer-id}")
  public void delete(@PathVariable String customerId, @RequestBody User user) {
    userService.isAuthentic(user);
    // TODO: needed here to verify that the user is deleting his own customer by asserting user.mail
    // = customer.mail
    customerService.delete(customerId);
  }
}
