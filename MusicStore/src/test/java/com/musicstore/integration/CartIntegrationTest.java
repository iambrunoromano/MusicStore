package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.CartController;
import com.musicstore.entity.Cart;
import com.musicstore.request.CartRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    List<Cart> cartList = cartController.getCart(FIRST_USER_MAIL);
    assertEquals(1, cartList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/cart.sql")
  public void saveTest() {
    cartController.save(SECOND_USER_MAIL, buildCartRequestList());
    List<Cart> cartList = cartController.getCart(SECOND_USER_MAIL);
    assertEquals(buildCartRequestList().size(), cartList.size());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/cart.sql")
  public void deleteTest() {
    cartController.delete(FIRST_USER_MAIL);
    List<Cart> cartList = cartController.getCart(FIRST_USER_MAIL);
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
