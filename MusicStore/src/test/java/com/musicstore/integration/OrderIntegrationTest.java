package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.OrderController;
import com.musicstore.entity.Cart;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class OrderIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  private static final String CREATE_ORDER_MAIL = "mail3@test";
  private static final String CREATE_ORDER_ADDRESS = "address_3";

  private final OrderController orderController;

  @Autowired
  public OrderIntegrationTest(OrderController orderController) {
    this.orderController = orderController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/order.sql")
  void getAllTest() {
    List<com.musicstore.entity.Order> orderList = orderController.getAll(FIRST_ADMIN_ID);
    assertEquals(2, orderList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/order.sql")
  void getByIdTest() {
    com.musicstore.entity.Order order = orderController.getById(3, FIRST_ADMIN_ID);
    assertEquals("address_1", order.getAddress());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/order.sql")
  void createTest() {
    HashMap<String, Object> actualMap =
        orderController.create(CREATE_ORDER_MAIL, CREATE_ORDER_ADDRESS);
    assertEquals(
        CREATE_ORDER_MAIL,
        ((com.musicstore.entity.Order) actualMap.get(OrderController.ORDER)).getMail());
    assertEquals(1, ((List<Cart>) actualMap.get(OrderController.DETAIL)).size());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/order.sql")
  void deleteTest() {
    orderController.delete(3, FIRST_ADMIN_ID);
    List<com.musicstore.entity.Order> orderList = orderController.getAll(FIRST_ADMIN_ID);
    assertEquals(1, orderList.size());
    com.musicstore.entity.Order leftOrder = orderList.get(0);
    assertEquals(2, leftOrder.getId());
  }
}
