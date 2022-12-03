package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: integration test
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

  @GetMapping(value = "/{mail}")
  public List<Cart> getCart(@PathVariable String mail) {
    userService.isUser(mail); // TODO: substitute this method with isAuthentic
    return cartService.getByMail(mail);
  }

  @PostMapping(value = "/{mail}")
  public Cart save(@PathVariable String mail, @RequestBody Cart cart) {
    userService.isUser(mail);
    return cartService.save(cart);
  }

  @DeleteMapping(value = "/{mail}")
  public void delete(@PathVariable String mail) {
    userService.isUser(mail);
    cartService.deleteByMail(mail);
  }
}
