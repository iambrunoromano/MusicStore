package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.User;
import com.musicstore.request.CartRequest;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "carts")
public class CartController {

  private final UserService userService;
  private final CartService cartService;

  @Autowired
  public CartController(UserService userService, CartService cartService) {
    this.userService = userService;
    this.cartService = cartService;
  }

  @GetMapping
  public ResponseEntity<List<Cart>> getCart(@RequestBody User user) {
    userService.isAuthentic(user);
    return ResponseEntity.ok(cartService.getByMail(user.getMail()));
  }

  @PostMapping
  public ResponseEntity<Void> save(
      @RequestHeader User user, @RequestBody List<CartRequest> cartRequestList) {
    userService.isAuthentic(user);
    cartService.save(cartRequestList);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody User user) {
    // TODO: make @RequestHeader User and Admin when passed to Rest API methods
    userService.isAuthentic(user);
    cartService.deleteByMail(user.getMail());
    return ResponseEntity.ok().build();
  }
}
