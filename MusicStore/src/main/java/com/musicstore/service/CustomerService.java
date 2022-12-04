package com.musicstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Customer;
import com.musicstore.entity.User;

@Service
public class CustomerService {

  @Autowired private com.musicstore.repository.CustomerRepository CustomerRepository;

  public Iterable<Customer> getAll() {
    return CustomerRepository.findAll();
  }

  public Optional<Customer> getById(String id) {
    return CustomerRepository.findById(id);
  }

  public Customer create(Customer p) {
    return CustomerRepository.save(p);
  }

  public Optional<Customer> update(String id, Customer p) {
    Optional<Customer> foundCustomer = CustomerRepository.findById(id);
    if (!foundCustomer.isPresent()) {
      return Optional.empty();
    }

    foundCustomer.get().setName(p.getName());
    foundCustomer.get().setSurname(p.getSurname());
    foundCustomer.get().setPaymentCard(p.getPaymentCard());
    foundCustomer.get().setBillingAddress(p.getBillingAddress());

    CustomerRepository.save(foundCustomer.get());
    return foundCustomer;
  }

  public boolean delete(String id) {
    Optional<Customer> foundCustomer = CustomerRepository.findById(id);
    if (!foundCustomer.isPresent()) {
      return false;
    }
    CustomerRepository.delete(foundCustomer.get());
    return false;
  }

  public boolean isCustomer(User wub) {
    Optional<Customer> customerFound = this.getById(wub.getMail());
    if (customerFound.isPresent())
      if (customerFound.get().getMail().equals(wub.getMail())) return true;
    return false;
  }
}
