package com.musicstore.repository;

import com.musicstore.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
  List<Product> findByProducer(String producer);

  List<Product> findByCategory(Integer category);

  List<Product> findByOrderBySoldQuantityDesc();

  @Query("SELECT DISTINCT(category) FROM product WHERE producer = ?1")
  List<Integer> findCategoriesByProducer(String producer);
}
