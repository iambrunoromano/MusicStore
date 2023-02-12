package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.OrderController;
import com.musicstore.entity.Cart;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<com.musicstore.entity.Order>> orderListResponseEntity =
        orderController.getAll(FIRST_ADMIN_ID);
    List<com.musicstore.entity.Order> orderList = orderListResponseEntity.getBody();
    assertEquals(2, orderList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/order.sql")
  void getByIdTest() {
    ResponseEntity<com.musicstore.entity.Order> orderResponseEntity =
        orderController.getById(3, FIRST_ADMIN_ID);
    com.musicstore.entity.Order order = orderResponseEntity.getBody();
    assertEquals("address_1", order.getAddress());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/order.sql")
  void createTest() {
    ResponseEntity<HashMap<String, Object>> mapResponseEntity =
        orderController.create(CREATE_ORDER_MAIL, CREATE_ORDER_ADDRESS);
    HashMap<String, Object> actualMap = mapResponseEntity.getBody();
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
    ResponseEntity<List<com.musicstore.entity.Order>> orderListResponseEntity =
        orderController.getAll(FIRST_ADMIN_ID);
    List<com.musicstore.entity.Order> orderList = orderListResponseEntity.getBody();
    assertEquals(1, orderList.size());
    com.musicstore.entity.Order leftOrder = orderList.get(0);
    assertEquals(2, leftOrder.getId());
  }
}
