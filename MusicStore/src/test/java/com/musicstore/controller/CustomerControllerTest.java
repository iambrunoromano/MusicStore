package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Customer;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

class CustomerControllerTest extends TestUtility {

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
              customerController.getAll(buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Customer>> customerListResponseEntity =
        customerController.getAll(buildUserRequest());
    List<Customer> customerList = customerListResponseEntity.getBody();
    assertEquals(CustomerServiceTest.buildCustomerList(), customerList);
  }

  @Test
  void getByIdNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.getById(buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
    ;
  }

  @Test
  void getByIdTest() {
    mockIsAuthentic();
    mockGetById();
    ResponseEntity<Customer> customerResponseEntity = customerController.getById(buildUserRequest());
    Customer customer = customerResponseEntity.getBody();
    assertEquals(CustomerServiceTest.buildCustomer(), customer);
  }

  @Test
  void createNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.create(buildUserRequest(), buildCustomer());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
  }

  @Test
  void createCustomerUserMismatchTest() {
    mockCustomerIsNotUser();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.create(buildUserRequest(), buildCustomer());
            });
    assertReasonException(
        actualException, HttpStatus.NOT_ACCEPTABLE, ReasonsConstant.USER_CUSTOMER_MISMATCH);
  }

  @Test
  void createTest() {
    mockIsAuthentic();
    mockSave();
    ResponseEntity<Customer> customerResponseEntity =
        customerController.create(UserServiceTest.buildUserRequest(), buildCustomer());
    Customer customer = customerResponseEntity.getBody();
    assertEquals(buildCustomer(), customer);
  }

  @Test
  void deleteNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.delete(CUSTOMER_ID, buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
  }

  @Test
  void deleteCustomerUserMismatchTest() {
    mockCustomerIsNotUser();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerController.delete(CUSTOMER_ID, buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.NOT_ACCEPTABLE, ReasonsConstant.USER_CUSTOMER_MISMATCH);
  }

  private void mockGetById() {
    BDDMockito.given(customerService.getById(Mockito.anyString()))
        .willReturn(Optional.of(buildCustomer()));
  }

  private void mockGetAll() {
    BDDMockito.given(customerService.getAll()).willReturn(buildCustomerList());
  }

  private void mockSave() {
    BDDMockito.given(customerService.save(Mockito.any())).willReturn(buildCustomer());
  }

  private void mockCustomerIsNotUser() {
    doThrow(
            new ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE, ReasonsConstant.USER_CUSTOMER_MISMATCH))
        .when(customerService)
        .customerIsUser(Mockito.any(), Mockito.any());
  }

  private void mockIsAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willReturn(buildUser());
  }

  private void mockIsNotAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(buildAdmin());
  }

  private void mockIsNotAdmin() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willThrow(expectedException);
  }
}
