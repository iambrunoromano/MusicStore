package com.musicstore.controller;

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

class ProductControllerTest {

  private static final String MAIL = "mail";

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final ProducerService producerService = Mockito.mock(ProducerService.class);
  private final ProductService productService = Mockito.mock(ProductService.class);

  private ProductController productController =
      new ProductController(adminService, producerService, productService);

  @Test
  void createAsProducerTest() {
    mockIsProducer();
    mockSave();
    ResponseEntity<Product> productResponseEntity =
        productController.createAsProducer(MAIL, ProductServiceTest.createProduct());
    Product product = productResponseEntity.getBody();
    assertEquals(ProductServiceTest.createProduct(), product);
  }

  @Test
  void createAsProducerNotProducerTest() {
    mockNotProducer();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.createAsProducer(MAIL, ProductServiceTest.createProduct());
            });
    ProducerServiceTest.assertProducerNotFoundException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  @Test
  void createAsAdminTest() {
    mockIsAdmin();
    mockSave();
    ResponseEntity<Product> productResponseEntity =
        productController.createAsAdmin(AdminControllerTest.ADMIN_AUTH_USER, ProductServiceTest.createProduct());
    Product product = productResponseEntity.getBody();
    assertEquals(ProductServiceTest.createProduct(), product);
  }

  @Test
  void createAsAdminNotAdminTest() {
    mockNotAdmin();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.createAsAdmin(AdminControllerTest.ADMIN_AUTH_USER, ProductServiceTest.createProduct());
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void deleteNotProducerTest() {
    mockNotProducer();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.delete(MAIL, ProductServiceTest.PRODUCT_ID);
            });
    ProducerServiceTest.assertProducerNotFoundException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  @Test
  void deleteNullMailTest() {
    mockIsProducer();
    mockFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.delete(null, ProductServiceTest.PRODUCT_ID);
            });
    ProducerServiceTest.assertProducerNotFoundException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
  }

  @Test
  void deleteInvalidProducerTest() {
    mockIsProducer();
    mockFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productController.delete("some-email", ProductServiceTest.PRODUCT_ID);
            });
    ProducerServiceTest.assertProducerNotFoundException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockNotProducer() {
    BDDMockito.given(producerService.isProducer(Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER));
  }

  private void mockIsProducer() {
    BDDMockito.given(producerService.isProducer(Mockito.anyString()))
        .willReturn(ProducerServiceTest.createProducer());
  }

  private void mockSave() {
    BDDMockito.given(productService.save(Mockito.any()))
        .willReturn(ProductServiceTest.createProduct());
  }

  private void mockFound() {
    BDDMockito.given(productService.getById(ProductServiceTest.PRODUCT_ID))
        .willReturn(ProductServiceTest.createProduct());
  }
}
