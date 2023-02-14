package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Order;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest extends TestUtility {

  private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
  private CartRepository cartRepository = Mockito.mock(CartRepository.class);
  private OrderService orderService = new OrderService(orderRepository, cartRepository);

  @Test
  void getVerifiedOrderTest() {
    mockFindOrderById();
    assertEquals(buildOrder(), orderService.getVerifiedOrder(ID, MAIL));
  }

  @Test
  void getVerifiedOrderNullMailTest() {
    doMailTest(null);
  }

  @Test
  void getVerifiedOrderInvalidEmailTest() {
    doMailTest("new-mail");
  }

  @Test
  void getVerifiedOrderNotFoundTest() {
    mockNotFoundOrderById();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderService.getVerifiedOrder(ID, MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND);
  }

  @Test
  void getAdminOrderTest() {
    mockFindOrderById();
    assertEquals(buildOrder(), orderService.getOrder(ID));
  }

  @Test
  void createTest() {
    mockCartListFound();
    Order actualOrder = orderService.create(MAIL, ADDRESS);
    assertEquals(MAIL, actualOrder.getMail());
    assertEquals(4.0, actualOrder.getTotal());
  }

  @Test
  void createCartNotFoundTest() {
    mockCartListNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderService.create(MAIL, ADDRESS);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
  }

  @Test
  void deleteTest() {
    mockFindOrderById();
    assertTrue(orderService.delete(ID));
  }

  @Test
  void deleteNotFoundTest() {
    mockNotFoundOrderById();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderService.delete(ID);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.ORDER_NOT_FOUND);
  }

  void doMailTest(String mail) {
    mockFindOrderById();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderService.getVerifiedOrder(ID, mail);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH);
  }

  private void mockCartListNotFound() {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(new ArrayList<>());
  }

  private void mockCartListFound() {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(buildCartList());
  }

  private void mockNotFoundOrderById() {
    BDDMockito.given(orderRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void mockFindOrderById() {
    BDDMockito.given(orderRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(buildOrder()));
  }
}
