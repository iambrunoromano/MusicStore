package com.musicstore.controller;

import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.service.AdminService;
import com.musicstore.service.CartService;
import com.musicstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

// TODO: external test
// TODO: Returned Response Entity HTTP Status

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

  @GetMapping(value = "/all/{admin-id}")
  public List<Order> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return orderService.getAll();
  }

  @GetMapping(value = "/{order-id}")
  public Order getById(@PathVariable int orderId, @RequestBody String mail) {
    return orderService.getVerifiedOrder(orderId, mail);
  }

  @PostMapping(value = "/{mail}")
  public HashMap<String, Object> create(@PathVariable String mail, @RequestBody String address) {
    // TODO: check all controller outputs and return Response classes instead of maps
    Order order = orderService.create(mail, address);
    order = orderService.save(order);
    HashMap<String, Object> orderMap = new HashMap<>();
    orderMap.put(ORDER, order);
    orderMap.put(DETAIL, getCartList(order.getId()));
    return orderMap;
  }

  @DeleteMapping(value = "/{order-id}")
  public void delete(@PathVariable int orderId, @RequestBody String adminId) {
    adminService.isAdmin(adminId);
    orderService.delete(orderId);
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
