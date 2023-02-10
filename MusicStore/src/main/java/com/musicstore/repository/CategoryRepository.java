package com.musicstore.repository;

import com.musicstore.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

  List<Category> findAll();

  @Query(
      value =
          "SELECT c from category c where id in (select distinct(category) FROM product WHERE producer = ?1)")
  List<Category> findCategoriesByProducer(String producer);
}
