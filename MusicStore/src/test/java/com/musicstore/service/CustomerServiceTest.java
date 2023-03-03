package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest extends TestUtility {

  private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
  private final CustomerService customerService = new CustomerService(customerRepository);

  @Test
  void getByIdNotFoundTest() {
    mockCustomerNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerService.getById(CUSTOMER_ID);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CUSTOMER_NOT_FOUND);
  }

  @Test
  void getByIdTest() {
    mockCustomerFound();
    assertEquals(Optional.of(buildCustomer()), customerService.getById(CUSTOMER_ID));
  }

  @Test
  void deleteNotFoundTest() {
    mockCustomerNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerService.delete(CUSTOMER_ID);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CUSTOMER_NOT_FOUND);
  }

  @Test
  void deleteTest() {
    mockCustomerFound();
    mockDelete();
    assertTrue(customerService.delete(CUSTOMER_ID));
  }

  @Test
  void customerIsNotUserTest() {
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              customerService.customerIsUser(buildCustomer(), buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.NOT_ACCEPTABLE, ReasonsConstant.USER_CUSTOMER_MISMATCH);
  }

  private void mockDelete() {
    BDDMockito.doNothing().when(customerRepository).delete(Mockito.any());
  }

  private void mockCustomerNotFound() {
    BDDMockito.given(customerRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
  }

  private void mockCustomerFound() {
    BDDMockito.given(customerRepository.findById(Mockito.anyString()))
        .willReturn(Optional.of(buildCustomer()));
  }
}
