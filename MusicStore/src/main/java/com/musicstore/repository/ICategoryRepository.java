package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.CategoryBean;

@Repository
public interface ICategoryRepository extends CrudRepository<CategoryBean, Integer>{
}
