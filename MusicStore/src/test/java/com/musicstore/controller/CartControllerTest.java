package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.User;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import com.musicstore.service.UserServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartControllerTest {

  private static final Integer START_LIST = 0;
  private static final Integer END_LIST = 2;
  private static final Integer PRODUCT_ID = 1;
  private static final Integer QUANTITY = 2;
  private static final String MAIL = "mail";
  private static final Timestamp DATE = Timestamp.from(Instant.now());

  private UserService userService = Mockito.mock(UserService.class);
  private CartService cartService = Mockito.mock(CartService.class);
  private CartController cartController = new CartController(userService, cartService);

  @Test
  void getCartTest() {
    mockIsUser();
    mockGetCartByMail();
    List<Cart> cartList = cartController.getCart(MAIL);
    for (Integer i = START_LIST; i < END_LIST; i++) {
      assertCart(cartList.get(i), i);
    }
  }

  @Test
  void getCartNotUserTest() {
    mockIsNotUser();
    mockGetCartByMail();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartController.getCart(MAIL);
            });
    UserServiceTest.assertNotUserException(actualException);
  }

  @Test
  void saveCartTest() {
    mockIsUser();
    mockSaveCart(PRODUCT_ID);
    Cart actualCart = cartController.save(MAIL, buildCart(PRODUCT_ID));
    assertCart(actualCart, PRODUCT_ID);
  }

  @Test
  void saveCartNotUserTest() {
    mockIsNotUser();
    mockSaveCart(PRODUCT_ID);
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartController.getCart(MAIL);
            });
    UserServiceTest.assertNotUserException(actualException);
  }

  @Test
  void deleteCartNotUserTest() {
    mockIsNotUser();
    mockDeleteCart();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartController.getCart(MAIL);
            });
    UserServiceTest.assertNotUserException(actualException);
  }

  private void mockDeleteCart() {
    BDDMockito.given(cartService.deleteByMail(Mockito.anyString())).willReturn(true);
  }

  private void mockSaveCart(Integer id) {
    BDDMockito.given(cartService.save(Mockito.any())).willReturn(buildCart(id));
  }

  private void mockGetCartByMail() {
    BDDMockito.given(cartService.getByMail(Mockito.anyString())).willReturn(buildCartList());
  }

  private void mockIsUser() {
    BDDMockito.given(userService.isUser(Mockito.anyString())).willReturn(new User());
  }

  private void mockIsNotUser() {
    BDDMockito.given(userService.isUser(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER));
  }

  private List<Cart> buildCartList() {
    List<Cart> cartList = new ArrayList<>();
    for (Integer i = START_LIST; i < END_LIST; ++i) {
      cartList.add(buildCart(i));
    }
    return cartList;
  }

  private Cart buildCart(Integer i) {
    return Cart.builder()
        .id(i)
        .productId(PRODUCT_ID + i)
        .quantity(QUANTITY)
        .mail(MAIL)
        .date(DATE)
        .build();
  }

  private void assertCart(Cart actualCart, Integer id) {
    assertEquals(id, actualCart.getId());
    assertEquals(PRODUCT_ID + id, actualCart.getProductId());
    assertEquals(QUANTITY, actualCart.getQuantity());
    assertEquals(MAIL, actualCart.getMail());
    assertEquals(DATE, actualCart.getDate());
  }
}
