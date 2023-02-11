package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.User;
import com.musicstore.request.CartRequest;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: external test
// TODO: Returned Response Entity HTTP Status

@RestController
@Slf4j
@RequestMapping(value = "cart")
public class CartController {

  private final UserService userService;
  private final CartService cartService;

  @Autowired
  public CartController(UserService userService, CartService cartService) {
    this.userService = userService;
    this.cartService = cartService;
  }

  @GetMapping
  public List<Cart> getCart(@RequestBody User user) {
    userService.isAuthentic(user);
    return cartService.getByMail(user.getMail());
  }

  @PostMapping
  public void save(@RequestHeader User user, @RequestBody List<CartRequest> cartRequestList) {
    userService.isAuthentic(user);
    cartService.save(cartRequestList);
  }

  @DeleteMapping
  public void delete(@RequestBody User user) {
    // TODO: make @RequestHeader User and Admin when passed to Rest API methods
    userService.isAuthentic(user);
    cartService.deleteByMail(user.getMail());
  }
}
