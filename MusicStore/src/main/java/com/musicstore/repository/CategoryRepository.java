package com.musicstore.repository;

import com.musicstore.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

  @Query(
      "SELECT id,name,parent,imgUrl from category where id in (select distinct(category) FROM product WHERE producer = ?1)")
  List<Category> findCategoriesByProducer(String producer);
}
