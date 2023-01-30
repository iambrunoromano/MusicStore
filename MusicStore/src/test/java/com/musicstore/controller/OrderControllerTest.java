package com.musicstore.controller;

import com.musicstore.service.AdminService;
import com.musicstore.service.CartService;
import com.musicstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

// TODO: implement all test methods

class OrderControllerTest {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private CartService cartService = Mockito.mock(CartService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);

  @Test
  void getAllTest() {}

  @Test
  void getAllNotAdminTest() {}

  @Test
  void getByIdTest() {}

  @Test
  void getByIdOrderNotFoundExceptionTest() {}

  @Test
  void getByIdOrderUserMismatchExceptionTest() {}

  @Test
  void createTest() {}

  @Test
  void createCartNotFoundExceptionTest() {}

  @Test
  void deleteNotAdminTest() {}

  @Test
  void deleteNotFoundTest() {}
}
