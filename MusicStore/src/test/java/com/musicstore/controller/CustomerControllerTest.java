package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Customer;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {

  // TODO: put all the static constants in a constant test class and use only them
  private static final String CUSTOMER_ID = "customer-id";

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final UserService userService = Mockito.mock(UserService.class);
  private final CustomerService customerService = Mockito.mock(CustomerService.class);

  private final CustomerController customerController =
      new CustomerController(adminService, userService, customerService);

  @Test
  void getAllNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.getAll(UserServiceTest.buildUser());
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    assertEquals(
        CustomerServiceTest.buildCustomerList(),
        customerController.getAll(UserServiceTest.buildUser()));
  }

  @Test
  void getByIdNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.getById(UserServiceTest.buildUser());
            });
    UserServiceTest.assertNotAuthenticException(actualException);
  }

  @Test
  void getByIdTest() {
    mockIsAuthentic();
    mockGetById();
    assertEquals(
        CustomerServiceTest.buildCustomer(),
        customerController.getById(UserServiceTest.buildUser()));
  }

  @Test
  void createNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.create(
                  UserServiceTest.buildUser(), CustomerServiceTest.buildCustomer());
            });
    UserServiceTest.assertNotAuthenticException(actualException);
  }

  @Test
  void createTest() {
    mockIsAuthentic();
    mockSave();
    assertEquals(
        CustomerServiceTest.buildCustomer(),
        customerController.create(
            UserServiceTest.buildUser(), CustomerServiceTest.buildCustomer()));
  }

  @Test
  void deleteNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.delete(CUSTOMER_ID, UserServiceTest.buildUser());
            });
    UserServiceTest.assertNotAuthenticException(actualException);
  }

  private void mockGetById() {
    BDDMockito.given(customerService.getById(Mockito.anyString()))
        .willReturn(Optional.of(CustomerServiceTest.buildCustomer()));
  }

  private void mockGetAll() {
    BDDMockito.given(customerService.getAll()).willReturn(CustomerServiceTest.buildCustomerList());
  }

  private void mockSave() {
    BDDMockito.given(customerService.save(Mockito.any()))
        .willReturn(CustomerServiceTest.buildCustomer());
  }

  private void mockIsAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willReturn(UserServiceTest.buildUser());
  }

  private void mockIsNotAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC));
  }

  private void mockIsAdmin() {
    // TODO: refactor all the mock methods in tests and make them parametrized to receive in input
    // mocked objects
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockIsNotAdmin() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    BDDMockito.given(adminService.isAdmin(Mockito.anyString())).willThrow(expectedException);
  }
}
