package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.CartBean;

@Repository
public interface ICartRepository extends CrudRepository<CartBean, Integer>{
}
