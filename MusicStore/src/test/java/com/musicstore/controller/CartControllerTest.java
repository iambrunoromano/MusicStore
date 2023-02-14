package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.User;
import com.musicstore.service.CartService;
import com.musicstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CartControllerTest extends TestUtility {

  private UserService userService = Mockito.mock(UserService.class);
  private CartService cartService = Mockito.mock(CartService.class);
  private CartController cartController = new CartController(userService, cartService);

  @Test
  void getCartTest() {
    mockIsUser();
    mockGetCartByMail();
    ResponseEntity<List<Cart>> cartListResponseEntity = cartController.getCart(buildUser());
    List<Cart> cartList = cartListResponseEntity.getBody();
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
              cartController.getCart(buildUser());
            });
    assertReasonException(actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  @Test
  void saveCartNotUserTest() {
    mockIsNotUser();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartController.getCart(buildUser());
            });
    assertReasonException(actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  @Test
  void deleteCartNotUserTest() {
    mockIsNotUser();
    mockDeleteCart();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartController.getCart(buildUser());
            });
    assertReasonException(actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  private void mockDeleteCart() {
    BDDMockito.given(cartService.deleteByMail(Mockito.anyString())).willReturn(true);
  }

  private void mockGetCartByMail() {
    BDDMockito.given(cartService.getByMail(Mockito.anyString())).willReturn(buildCartList());
  }

  private void mockIsUser() {
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willReturn(new User());
  }

  private void mockIsNotUser() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER));
  }
}
