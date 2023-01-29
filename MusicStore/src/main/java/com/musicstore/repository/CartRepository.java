package com.musicstore.repository;

import com.musicstore.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
  List<Cart> findByMail(String mail);

  List<Cart> findByOrderId(int orderId);
}
