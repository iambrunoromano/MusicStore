package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.OrderBean;

@Repository
public interface IOrderRepository extends CrudRepository<OrderBean, Integer>{
}
