package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.response.OrderResponse;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderControllerTest extends TestUtility {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private CartService cartService = Mockito.mock(CartService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);
  private UserService userService = Mockito.mock(UserService.class);

  private OrderController orderController =
      new OrderController(adminService, cartService, orderService, userService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Order>> orderListResponseEntity = orderController.getAll(FIRST_ADMIN_USER);
    List<Order> orderList = orderListResponseEntity.getBody();
    assertEquals(buildOrderList(), orderList);
  }

  @Test
  void getAllNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getAll(FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getByIdTest() {
    mockGetVerifiedOrder();
    ResponseEntity<Order> orderResponseEntity =
        orderController.getById(OrderServiceTest.ID, buildAuthenticUser());
    Order order = orderResponseEntity.getBody();
    assertEquals(OrderServiceTest.buildOrder(), order);
  }

  @Test
  void getByIdOrderNotFoundExceptionTest() {
    mockGetVerifiedOrderOrderNotFoundException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getById(OrderServiceTest.ID, buildAuthenticUser());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND);
  }

  @Test
  void getByIdOrderUserMismatchExceptionTest() {
    mockGetVerifiedOrderOrderUserMismatchException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getById(OrderServiceTest.ID, buildAuthenticUser());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH);
  }

  @Test
  void createTest() {
    mockCreate();
    ResponseEntity<OrderResponse> responseEntity =
        orderController.create(buildAuthenticUser(), OrderServiceTest.ADDRESS);
    OrderResponse orderResponse = responseEntity.getBody();
    assertEquals(buildOrder(), orderResponse.getOrder());
    List<Cart> expectedCartList = OrderServiceTest.buildCartList();
    for (Cart cart : expectedCartList) {
      cart.setBought(true);
      cart.setOrderId(OrderServiceTest.ID);
    }
    assertEquals(expectedCartList, orderResponse.getCartList());
  }

  @Test
  void createCartNotFoundExceptionTest() {
    mockCreateNoCartFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.create(buildAuthenticUser(), OrderServiceTest.ADDRESS);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
  }

  @Test
  void deleteNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.delete(OrderServiceTest.ID, FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void deleteNotFoundTest() {
    mockDeleteOrderNotFoundException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.delete(OrderServiceTest.ID, FIRST_ADMIN_USER);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.ORDER_NOT_FOUND);
  }

  void mockIsNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(buildAdmin());
  }

  void mockCreateNoCartFound() {
    BDDMockito.given(orderService.create(Mockito.anyString(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND));
  }

  void mockCreate() {
    BDDMockito.given(orderService.create(Mockito.anyString(), Mockito.anyString()))
        .willReturn(OrderServiceTest.buildOrder());
    BDDMockito.given(orderService.save(Mockito.any())).willReturn(OrderServiceTest.buildOrder());
    BDDMockito.given(cartService.getByOrderId(Mockito.anyInt()))
        .willReturn(OrderServiceTest.buildCartList());
  }

  void mockGetVerifiedOrderOrderUserMismatchException() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH));
  }

  void mockGetVerifiedOrderOrderNotFoundException() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND));
  }

  void mockDeleteOrderNotFoundException() {
    BDDMockito.given(orderService.delete(Mockito.anyInt()))
        .willThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.ORDER_NOT_FOUND));
  }

  void mockGetVerifiedOrder() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willReturn(OrderServiceTest.buildOrder());
  }

  void mockGetAll() {
    BDDMockito.given(orderService.getAll()).willReturn(buildOrderList());
  }
}
