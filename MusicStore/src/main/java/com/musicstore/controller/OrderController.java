package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.entity.User;
import com.musicstore.response.OrderResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.CartService;
import com.musicstore.service.OrderService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

  private final AdminService adminService;
  private final CartService cartService;
  private final OrderService orderService;
  private final UserService userService;

  @Autowired
  public OrderController(
      AdminService adminService,
      CartService cartService,
      OrderService orderService,
      UserService userService) {
    this.adminService = adminService;
    this.cartService = cartService;
    this.orderService = orderService;
    this.userService = userService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Order>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(orderService.getAll());
  }

  @GetMapping(value = "/{order-id}")
  public ResponseEntity<Order> getById(@PathVariable int orderId, @RequestHeader User user) {
    userService.isAuthentic(user);
    return ResponseEntity.ok(orderService.getVerifiedOrder(orderId, user.getMail()));
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(
      @RequestHeader User user, @RequestBody String address) {
    userService.isAuthentic(user);
    Order order = orderService.create(user.getMail(), address);
    order = orderService.save(order);
    OrderResponse response =
        OrderResponse.builder().order(order).cartList(getCartList(order.getId())).build();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "/{order-id}")
  public ResponseEntity<Void> delete(@PathVariable int orderId, @RequestHeader User user) {
    adminService.isAdmin(user);
    orderService.delete(orderId);
    return ResponseEntity.ok().build();
  }

  private List<Cart> getCartList(int orderId) {
    List<Cart> cartList = cartService.getByOrderId(orderId);
    for (Cart cart : cartList) {
      cart.setBought(true);
      cart.setOrderId(orderId);
    }
    return cartList;
  }
}
