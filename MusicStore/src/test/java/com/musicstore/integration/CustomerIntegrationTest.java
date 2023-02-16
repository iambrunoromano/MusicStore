package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.controller.CustomerController;
import com.musicstore.entity.Customer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class CustomerIntegrationTest extends TestUtility {

  private final CustomerController customerController;

  @Autowired
  public CustomerIntegrationTest(CustomerController customerController) {
    this.customerController = customerController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/customer.sql")
  void getAllTest() {
    ResponseEntity<List<Customer>> customerListResponseEntity =
        customerController.getAll(buildAdminUser());
    List<Customer> customerList = customerListResponseEntity.getBody();
    assertEquals(2, customerList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/customer.sql")
  void getByIdTest() {
    ResponseEntity<Customer> customerResponseEntity =
        customerController.getById(buildAuthenticUser());
    Customer customer = customerResponseEntity.getBody();
    assertEquals(USER_ID, customer.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/customer.sql")
  void createTest() {
    ResponseEntity<Customer> customerResponseEntity =
        customerController.create(genericBuildUser(CUSTOMER_ID, USER_PASSWORD), buildCustomer());
    Customer customer = customerResponseEntity.getBody();
    customer.setInsertDate(null);
    customer.setUpdateDate(null);
    assertEquals(buildCustomer(), customer);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/customer.sql")
  void deleteTest() {
    ResponseEntity<Void> responseEntity = customerController.delete(USER_ID, buildAuthenticUser());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.getById(buildAuthenticUser());
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CUSTOMER_NOT_FOUND);
  }
}
