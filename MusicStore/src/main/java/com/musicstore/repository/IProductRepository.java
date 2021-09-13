package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.ProductBean;

public interface IProductRepository extends CrudRepository<ProductBean, Integer>{
}
