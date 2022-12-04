package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Customer;
import com.musicstore.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

  private static final String CUSTOMER_ID = "customer-id";

  private static final String NAME = "name";

  private static final String SURNAME = "surname";

  private static final String PAYMENT_CARD = "payment-card";

  private static final String BILLING_ADDRESS = "billing-address";

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
    assertCustomerNotFoundException(actualException);
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
    assertCustomerNotFoundException(actualException);
  }

  @Test
  void deleteTest() {
    mockCustomerFound();
    mockDelete();
    assertTrue(customerService.delete(CUSTOMER_ID));
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

  public static void assertCustomerNotFoundException(ResponseStatusException actualException) {
    assertGenericCustomerException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CUSTOMER_NOT_FOUND);
  }

  private static void assertGenericCustomerException(
      ResponseStatusException actualException, HttpStatus httpStatus, String reasonsConstant) {
    // TODO: refactor deleting all those assertGeneric_Exception methods and put them in a test
    // utility class aside as assertReasonException
    ResponseStatusException expectedException =
        new ResponseStatusException(httpStatus, reasonsConstant);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  private static Customer buildCustomer() {
    // TODO: centralize all entities build methods in a static test utils class and alwasys use that
    return Customer.builder()
        .mail(CUSTOMER_ID)
        .name(NAME)
        .surname(SURNAME)
        .paymentCard(PAYMENT_CARD)
        .billingAddress(BILLING_ADDRESS)
        .build();
  }
}
