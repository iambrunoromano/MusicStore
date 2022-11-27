package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {}
