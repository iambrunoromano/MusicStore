package com.musicstore.repository;

import com.musicstore.model.OrderBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderBean, Integer> {}
