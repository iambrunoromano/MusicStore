package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.ProductBean;

@Repository
public interface IProductRepository extends CrudRepository<ProductBean, Integer>{
}
