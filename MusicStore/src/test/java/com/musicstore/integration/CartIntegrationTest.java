package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.CartController;
import com.musicstore.entity.Cart;
import com.musicstore.entity.User;
import com.musicstore.request.CartRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class CartIntegrationTest {

  private final CartController cartController;
  private static final String FIRST_USER_MAIL = "usermail1@test";
  private static final String SECOND_USER_MAIL = "usermail2@test";

  @Autowired
  public CartIntegrationTest(CartController cartController) {
    this.cartController = cartController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/cart.sql")
  public void getCartTest() {
    User authUser =
        UserIntegrationTest.buildUser(
            UserIntegrationTest.FIRST_USER_ID, UserIntegrationTest.FIRST_USER_PASSWORD);
    ResponseEntity<List<Cart>> cartListResponseEntity = cartController.getCart(authUser);
    List<Cart> cartList = cartListResponseEntity.getBody();
    assertEquals(1, cartList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/cart.sql")
  public void saveTest() {
    User authUser =
        UserIntegrationTest.buildUser(
            UserIntegrationTest.SECOND_USER_ID, UserIntegrationTest.SECOND_USER_PASSWORD);
    ResponseEntity<Void> responseEntity = cartController.save(authUser, buildCartRequestList());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseEntity<List<Cart>> cartListResponseEntity = cartController.getCart(authUser);
    List<Cart> cartList = cartListResponseEntity.getBody();
    assertEquals(buildCartRequestList().size(), cartList.size());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/cart.sql")
  public void deleteTest() {
    User authUser =
        UserIntegrationTest.buildUser(
            UserIntegrationTest.FIRST_USER_ID, UserIntegrationTest.FIRST_USER_PASSWORD);
    ResponseEntity<Void> responseEntity = cartController.delete(authUser);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseEntity<List<Cart>> cartListResponseEntity = cartController.getCart(authUser);
    List<Cart> cartList = cartListResponseEntity.getBody();
    assertEquals(0, cartList.size());
  }

  private List<CartRequest> buildCartRequestList() {
    List<CartRequest> cartRequestList = new ArrayList<>();
    cartRequestList.add(buildCartRequest());
    cartRequestList.add(buildCartRequest());
    return cartRequestList;
  }

  private CartRequest buildCartRequest() {
    return CartRequest.builder().productId(1).quantity(1).mail(SECOND_USER_MAIL).build();
  }
}
