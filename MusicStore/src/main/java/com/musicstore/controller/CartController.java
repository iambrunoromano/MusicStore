package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.request.CartRequest;
import com.musicstore.request.UserRequest;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
  public ResponseEntity<List<Cart>> getCart(@RequestHeader UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    return ResponseEntity.ok(cartService.getByMail(userRequest.getMail()));
  }

  @PostMapping
  public ResponseEntity<Void> save(
      @RequestHeader UserRequest userRequest, @RequestBody List<CartRequest> cartRequestList) {
    userService.isAuthentic(userRequest);
    cartService.save(cartRequestList);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestHeader UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    cartService.deleteByMail(userRequest.getMail());
    return ResponseEntity.ok().build();
  }
}
