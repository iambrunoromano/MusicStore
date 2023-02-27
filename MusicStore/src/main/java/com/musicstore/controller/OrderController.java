package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.entity.User;
import com.musicstore.response.OrderResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.CartService;
import com.musicstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "orders")
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
    // TODO: insert userService and authenticate in all calls
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Order>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(orderService.getAll());
  }

  @GetMapping(value = "/{order-id}")
  public ResponseEntity<Order> getById(@PathVariable int orderId, @RequestHeader User user) {
    // TODO: fix all tests
    return ResponseEntity.ok(orderService.getVerifiedOrder(orderId, user.getMail()));
  }

  @PostMapping(value = "/{mail}")
  public ResponseEntity<OrderResponse> create(
      @PathVariable String mail, @RequestBody String address) {
    Order order = orderService.create(mail, address);
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
