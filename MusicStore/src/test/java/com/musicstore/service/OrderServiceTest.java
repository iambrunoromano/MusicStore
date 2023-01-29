package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Order;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

  private static final int ID = 0;
  private static final String MAIL = "mail";
  private static final Timestamp DATE = Timestamp.from(Instant.now());
  private static final double TOTAL = 1.0;

  private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
  private CartRepository cartRepository = Mockito.mock(CartRepository.class);
  private OrderService orderService = new OrderService(orderRepository, cartRepository);

  @Test
  void getVerifiedOrderTest() {
    mockFindOrderById();
    assertEquals(createOrder(), orderService.getVerifiedOrder(ID, MAIL));
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
    assertOrderNotFoundException(actualException);
  }

  void doMailTest(String mail) {
    mockFindOrderById();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              orderService.getVerifiedOrder(ID, mail);
            });
    assertOrderUserMismatchException(actualException);
  }

  private void mockNotFoundOrderById() {
    BDDMockito.given(orderRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void mockFindOrderById() {
    BDDMockito.given(orderRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(createOrder()));
  }

  private Order createOrder() {
    return Order.builder().id(ID).mail(MAIL).date(DATE).total(TOTAL).build();
  }

  private static void assertOrderUserMismatchException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(
            HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  private static void assertOrderNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
