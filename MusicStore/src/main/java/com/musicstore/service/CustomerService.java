package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Customer;
import com.musicstore.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  public Optional<Customer> getById(String customerId) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    if (!optionalCustomer.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CUSTOMER_NOT_FOUND);
    }
    return optionalCustomer;
  }

  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  public boolean delete(String customerId) {
    Optional<Customer> optionalCustomer = this.getById(customerId);
    customerRepository.delete(optionalCustomer.get());
    return true;
  }
}
