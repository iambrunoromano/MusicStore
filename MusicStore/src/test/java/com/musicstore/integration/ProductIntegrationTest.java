package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.ProductController;
import com.musicstore.entity.Product;
import com.musicstore.service.ProductServiceTest;
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
public class ProductIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  private static final String FIRST_PRODUCT_NAME = "product_1";
  private static final String FIRST_PRODUCER_MAIL = "producermail1@test";
  private final ProductController productController;

  @Autowired
  public ProductIntegrationTest(ProductController productController) {
    this.productController = productController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/product.sql")
  void getByCategoryTest() {
    ResponseEntity<List<Product>> productListResponseEntity = productController.getByCategory(1);
    List<Product> productList = productListResponseEntity.getBody();
    assertEquals(1, productList.size());
    Product actualProduct = productList.get(0);
    assertEquals(FIRST_PRODUCT_NAME, actualProduct.getName());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/product.sql")
  void getByProducerTest() {
    ResponseEntity<List<Product>> productListResponseEntity =
        productController.getByProducer(FIRST_PRODUCER_MAIL);
    List<Product> productList = productListResponseEntity.getBody();
    assertEquals(1, productList.size());
    Product actualProduct = productList.get(0);
    assertEquals(FIRST_PRODUCT_NAME, actualProduct.getName());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/product.sql")
  void getBestTest() {
    ResponseEntity<List<Product>> productListResponseEntity = productController.getBest();
    List<Product> productList = productListResponseEntity.getBody();
    assertEquals(2, productList.size());
    Product actualProduct = productList.get(1);
    assertEquals(FIRST_PRODUCT_NAME, actualProduct.getName());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/product.sql")
  void getAllTest() {
    ResponseEntity<List<Product>> productListResponseEntity = productController.getAll();
    List<Product> productList = productListResponseEntity.getBody();
    assertEquals(2, productList.size());
  }

  @Test
  @Order(5)
  @Sql("classpath:integration/product.sql")
  void getByIdTest() {
    ResponseEntity<Product> productResponseEntity = productController.getById(1);
    Product product = productResponseEntity.getBody();
    assertEquals(FIRST_PRODUCT_NAME, product.getName());
  }

  @Test
  @Order(6)
  @Sql("classpath:integration/product.sql")
  void createAsProducerTest() {
    ResponseEntity<Product> productResponseEntity =
        productController.createAsProducer(FIRST_PRODUCER_MAIL, ProductServiceTest.createProduct());
    Product product = productResponseEntity.getBody();
    product.setId(ProductServiceTest.createProduct().getId());
    product.setInsertDate(null);
    product.setUpdateDate(null);
    assertEquals(ProductServiceTest.createProduct(), product);
  }

  @Test
  @Order(7)
  @Sql("classpath:integration/product.sql")
  void createAsAdminTest() {
    ResponseEntity<Product> productResponseEntity =
        productController.createAsAdmin(FIRST_ADMIN_ID, ProductServiceTest.createProduct());
    Product product = productResponseEntity.getBody();
    product.setId(ProductServiceTest.createProduct().getId());
    product.setInsertDate(null);
    product.setUpdateDate(null);
    assertEquals(ProductServiceTest.createProduct(), product);
  }

  @Test
  @Order(8)
  @Sql("classpath:integration/product.sql")
  void deleteTest() {
    productController.delete(FIRST_PRODUCER_MAIL, 1);
    ResponseEntity<List<Product>> productListResponseEntity = productController.getAll();
    List<Product> productList = productListResponseEntity.getBody();
    assertEquals(1, productList.size());
    Product leftProduct = productList.get(0);
    assertEquals(2, leftProduct.getId());
  }
}
