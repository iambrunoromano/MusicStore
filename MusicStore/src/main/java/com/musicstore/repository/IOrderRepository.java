package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.OrderBean;

public interface IOrderRepository extends CrudRepository<OrderBean, Integer>{
}