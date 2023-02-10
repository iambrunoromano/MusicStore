package com.musicstore.repository;

import com.musicstore.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

  List<Customer> findAll();
}
