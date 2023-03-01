package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductControllerTest extends TestUtility {

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final ProducerService producerService = Mockito.mock(ProducerService.class);
  private final ProductService productService = Mockito.mock(ProductService.class);
  private final UserService userService = Mockito.mock(UserService.class);

  private ProductController productController =
      new ProductController(adminService, producerService, productService, userService);

  @Test
  void createAsProducerTest() {
    mockIsAuthentic();
    mockIsProducer();
    mockSave();
    ResponseEntity<Product> productResponseEntity =
        productController.createAsProducer(buildAuthenticUser(), ProductServiceTest.buildProduct());
    Product product = productResponseEntity.getBody();
    assertEquals(ProductServiceTest.buildProduct(), product);
  }

  @Test
  void createAsProducerNotProducerTest() {
    mockIsAuthentic();
    mockNotProducer();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.createAsProducer(
                  buildAuthenticUser(), ProductServiceTest.buildProduct());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  @Test
  void createAsAdminTest() {
    mockIsAdmin();
    mockSave();
    ResponseEntity<Product> productResponseEntity =
        productController.createAsAdmin(FIRST_ADMIN_USER, ProductServiceTest.buildProduct());
    Product product = productResponseEntity.getBody();
    assertEquals(ProductServiceTest.buildProduct(), product);
  }

  @Test
  void createAsAdminNotAdminTest() {
    mockNotAdmin();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.createAsAdmin(FIRST_ADMIN_USER, ProductServiceTest.buildProduct());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void deleteNotProducerTest() {
    mockNotProducer();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.delete(buildAuthenticUser(), ProductServiceTest.PRODUCT_ID);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  @Test
  void deleteInvalidProducerTest() {
    mockIsProducer();
    mockFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.delete(buildUser(), ProductServiceTest.PRODUCT_ID);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(buildAdmin());
  }

  private void mockNotProducer() {
    BDDMockito.given(producerService.isProducer(Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER));
  }

  private void mockIsAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willReturn(buildAuthenticUser());
  }

  private void mockIsProducer() {
    BDDMockito.given(producerService.isProducer(Mockito.anyString()))
        .willReturn(ProducerServiceTest.buildProducer());
  }

  private void mockSave() {
    BDDMockito.given(productService.save(Mockito.any()))
        .willReturn(ProductServiceTest.buildProduct());
  }

  private void mockFound() {
    BDDMockito.given(productService.getById(ProductServiceTest.PRODUCT_ID))
        .willReturn(ProductServiceTest.buildProduct());
  }
}
