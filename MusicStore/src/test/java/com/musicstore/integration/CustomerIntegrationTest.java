package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.CustomerController;
import com.musicstore.entity.Customer;
import com.musicstore.entity.User;
import com.musicstore.service.CustomerServiceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class CustomerIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  private static final String USER_ID = "usermail1@test";
  private static final String USER_PASSWORD = "password1";

  private final CustomerController customerController;

  @Autowired
  public CustomerIntegrationTest(CustomerController customerController) {
    this.customerController = customerController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/customer.sql")
  void getAllTest() {
    List<Customer> customerList = customerController.getAll(buildAdminUser());
    assertEquals(2, customerList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/customer.sql")
  void getByIdTest() {
    Customer customer = customerController.getById(buildAuthenticUser());
    assertEquals(USER_ID, customer.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/customer.sql")
  void createTest() {
    Customer customer =
        customerController.create(buildAuthenticUser(), CustomerServiceTest.buildCustomer());
    customer.setInsertDate(null);
    customer.setUpdateDate(null);
    assertEquals(CustomerServiceTest.buildCustomer(), customer);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/customer.sql")
  void deleteTest() {
    customerController.delete(USER_ID, buildAuthenticUser());
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.getById(buildAuthenticUser());
            });
    CustomerServiceTest.assertCustomerNotFoundException(actualException);
  }

  private User buildAdminUser() {
    return User.builder().mail(FIRST_ADMIN_ID).build();
  }

  private User buildAuthenticUser() {
    return User.builder().mail(USER_ID).password(USER_PASSWORD).build();
  }
}
