package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.CustomerBean;

public interface ICustomerRepository extends CrudRepository<CustomerBean, Integer>{
}
