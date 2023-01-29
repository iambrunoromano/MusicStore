package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class CartService {

  private final CartRepository cartRepository;

  @Autowired
  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Iterable<Cart> getAll() {
    return cartRepository.findAll();
  }

  public List<Cart> getByMail(String mail) {
    return cartRepository.findByMail(mail);
  }

  public List<Cart> getByOrderId(int orderId) {
    return cartRepository.findByOrderId(orderId);
  }

  public Cart save(Cart cart) {
    return cartRepository.save(cart);
  }

  public boolean deleteByMail(String mail) {
    List<Cart> cartList = this.getByMail(mail);
    if (cartList.isEmpty()) {
      log.info("No cart rows found for user with mail [{}]", mail);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
    }
    for (Cart cart : cartList) {
      cartRepository.delete(cart);
    }
    return true;
  }
}
