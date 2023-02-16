package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.TestUtility;
import com.musicstore.controller.OrderController;
import com.musicstore.response.OrderResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class OrderIntegrationTest extends TestUtility {

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
        orderController.getAll(FIRST_ADMIN_USER);
    List<com.musicstore.entity.Order> orderList = orderListResponseEntity.getBody();
    assertEquals(2, orderList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/order.sql")
  void getByIdTest() {
    ResponseEntity<com.musicstore.entity.Order> orderResponseEntity =
        orderController.getById(3, FIRST_ADMIN_USER.getMail());
    com.musicstore.entity.Order order = orderResponseEntity.getBody();
    assertEquals("address_1", order.getAddress());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/order.sql")
  void createTest() {
    ResponseEntity<OrderResponse> orderResponseEntity =
        orderController.create(CREATE_ORDER_MAIL, CREATE_ORDER_ADDRESS);
    OrderResponse response = orderResponseEntity.getBody();
    assertEquals(CREATE_ORDER_MAIL, response.getOrder().getMail());
    assertEquals(1, response.getCartList().size());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/order.sql")
  void deleteTest() {
    orderController.delete(3, FIRST_ADMIN_USER);
    ResponseEntity<List<com.musicstore.entity.Order>> orderListResponseEntity =
        orderController.getAll(FIRST_ADMIN_USER);
    List<com.musicstore.entity.Order> orderList = orderListResponseEntity.getBody();
    assertEquals(1, orderList.size());
    com.musicstore.entity.Order leftOrder = orderList.get(0);
    assertEquals(2, leftOrder.getId());
  }
}
