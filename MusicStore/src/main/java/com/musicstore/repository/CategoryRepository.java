package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {}
