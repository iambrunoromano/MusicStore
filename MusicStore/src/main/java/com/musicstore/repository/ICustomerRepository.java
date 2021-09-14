package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.CustomerBean;

@Repository
public interface ICustomerRepository extends CrudRepository<CustomerBean, Integer>{
}
