package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {}
