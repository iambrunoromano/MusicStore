package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
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

class OrderControllerTest {

  private static final String ADMIN_ID = "admin-id";

  private AdminService adminService = Mockito.mock(AdminService.class);
  private CartService cartService = Mockito.mock(CartService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);

  private OrderController orderController =
      new OrderController(adminService, cartService, orderService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Order>> orderListResponseEntity =
        orderController.getAll(AdminControllerTest.ADMIN_AUTH_USER);
    List<Order> orderList = orderListResponseEntity.getBody();
    assertEquals(createOrderList(), orderList);
  }

  @Test
  void getAllNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getAll(AdminControllerTest.ADMIN_AUTH_USER);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getByIdTest() {
    mockGetVerifiedOrder();
    ResponseEntity<Order> orderResponseEntity =
        orderController.getById(OrderServiceTest.ID, OrderServiceTest.MAIL);
    Order order = orderResponseEntity.getBody();
    assertEquals(OrderServiceTest.createOrder(), order);
  }

  @Test
  void getByIdOrderNotFoundExceptionTest() {
    mockGetVerifiedOrderOrderNotFoundException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getById(OrderServiceTest.ID, OrderServiceTest.MAIL);
            });
    OrderServiceTest.assertOrderNotFoundException(actualException, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Test
  void getByIdOrderUserMismatchExceptionTest() {
    mockGetVerifiedOrderOrderUserMismatchException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.getById(OrderServiceTest.ID, OrderServiceTest.MAIL);
            });
    OrderServiceTest.assertOrderUserMismatchException(actualException);
  }

  @Test
  void createTest() {
    mockCreate();
    ResponseEntity<HashMap<String, Object>> mapResponseEntity =
        orderController.create(OrderServiceTest.MAIL, OrderServiceTest.ADDRESS);
    HashMap<String, Object> actualMap = mapResponseEntity.getBody();
    assertEquals(OrderServiceTest.createOrder(), actualMap.get(OrderController.ORDER));
    List<Cart> expectedCartList = OrderServiceTest.createCartList();
    for (Cart cart : expectedCartList) {
      cart.setBought(true);
      cart.setOrderId(OrderServiceTest.ID);
    }
    assertEquals(expectedCartList, actualMap.get(OrderController.DETAIL));
  }

  @Test
  void createCartNotFoundExceptionTest() {
    mockCreateNoCartFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.create(OrderServiceTest.MAIL, OrderServiceTest.ADDRESS);
            });
    CartServiceTest.assertCartNotFoundException(actualException);
  }

  @Test
  void deleteNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.delete(OrderServiceTest.ID, AdminControllerTest.ADMIN_AUTH_USER);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void deleteNotFoundTest() {
    mockDeleteOrderNotFoundException();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderController.delete(OrderServiceTest.ID, AdminControllerTest.ADMIN_AUTH_USER);
            });
    OrderServiceTest.assertOrderNotFoundException(actualException, HttpStatus.NOT_FOUND);
  }

  void mockIsNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(AdminServiceTest.buildAdmin());
  }

  void mockCreateNoCartFound() {
    BDDMockito.given(orderService.create(Mockito.anyString(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND));
  }

  void mockCreate() {
    BDDMockito.given(orderService.create(Mockito.anyString(), Mockito.anyString()))
        .willReturn(OrderServiceTest.createOrder());
    BDDMockito.given(orderService.save(Mockito.any())).willReturn(OrderServiceTest.createOrder());
    BDDMockito.given(cartService.getByOrderId(Mockito.anyInt()))
        .willReturn(OrderServiceTest.createCartList());
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
        .willReturn(OrderServiceTest.createOrder());
  }

  void mockGetAll() {
    BDDMockito.given(orderService.getAll()).willReturn(createOrderList());
  }

  private List<Order> createOrderList() {
    List<Order> orderList = new ArrayList<>();
    orderList.add(OrderServiceTest.createOrder());
    orderList.add(OrderServiceTest.createOrder());
    return orderList;
  }
}
