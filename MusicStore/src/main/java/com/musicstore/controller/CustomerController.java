package com.musicstore.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Customer;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CustomerService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;

@RestController
public class CustomerController {

  // TODO: understand how the customer route differs in the usage from user route

  @Autowired private AdminService adminService;

  @Autowired private UserService webuserService;

  @Autowired private CustomerService customerService;

  public CustomerController() {}

  @RequestMapping(value = "/musicstore/api/customer/all", method = RequestMethod.POST)
  public Iterable<Customer> getAll(@RequestBody User b) {
    adminService.isAdmin(b.getMail());
    return customerService.getAll();
  }

  @RequestMapping(value = "/musicstore/api/customer/{id}", method = RequestMethod.POST)
  public Customer getById(@PathVariable String id, @RequestBody User b) {
    adminService.isAdmin(b.getMail());
    webuserService.isAuthentic(b);
    if (b.getMail().equals(id)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return customerService.getById(id).get();
  }

  @RequestMapping(value = "/musicstore/api/customer", method = RequestMethod.POST)
  public Customer create(@RequestBody Customer p) {
    return customerService.save(p);
  }

  @RequestMapping(value = "/musicstore/api/customer/{id}", method = RequestMethod.PUT)
  public Customer update(
      @PathVariable String id, @RequestBody Map<String, Map<String, String>> map) {
    Customer cb = Utility.customerDeMap(map.get("toput"));
    User b = Utility.webuserDeMap(map.get("authorized"));
    adminService.isAdmin(b.getMail());
    webuserService.isAuthentic(b);
    if (!b.getMail().equals(cb.getMail())) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized user");
    }
    Customer updatedCustomer = customerService.save(cb);
    return updatedCustomer;
  }

  @RequestMapping(value = "/musicstore/api/customer/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String id, @RequestBody User b) {
    adminService.isAdmin(b.getMail());
    webuserService.isAuthentic(b);
    if (!b.getMail().equals(customerService.getById(id).get().getMail())) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    Boolean isDeleted = customerService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
  }
}
