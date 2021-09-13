package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.CategoryBean;

public interface ICategoryRepository extends CrudRepository<CategoryBean, Integer>{
}