package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CartService;
import com.musicstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "order")
public class OrderController {

  public static final String ORDER = "order";
  public static final String DETAIL = "detail";

  private final AdminService adminService;
  private final CartService cartService;
  private final OrderService orderService;

  @Autowired
  public OrderController(
      AdminService adminService, CartService cartService, OrderService orderService) {
    this.adminService = adminService;
    this.cartService = cartService;
    this.orderService = orderService;
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(orderService.getAll());
  }

  @GetMapping(value = "/{order-id}")
  public ResponseEntity<Order> getById(@PathVariable int orderId, @RequestBody String mail) {
    return ResponseEntity.ok(orderService.getVerifiedOrder(orderId, mail));
  }

  @PostMapping(value = "/{mail}")
  public ResponseEntity<HashMap<String, Object>> create(
      @PathVariable String mail, @RequestBody String address) {
    // TODO: return response instead of map
    Order order = orderService.create(mail, address);
    order = orderService.save(order);
    HashMap<String, Object> orderMap = new HashMap<>();
    orderMap.put(ORDER, order);
    orderMap.put(DETAIL, getCartList(order.getId()));
    return ResponseEntity.ok(orderMap);
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
