package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.CartBean;

public interface ICartRepository extends CrudRepository<CartBean, Integer>{
}
