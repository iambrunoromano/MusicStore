package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.ProductController;
import com.musicstore.entity.Producer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class ProductIntegrationTest {

  private final ProductController productController;

  @Autowired
  public ProductIntegrationTest(ProductController productController) {
    this.productController = productController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/product.sql")
  void getByCategoryTest() {}

  @Test
  @Order(2)
  @Sql("classpath:integration/product.sql")
  void getByProducerTest() {}

  @Test
  @Order(3)
  @Sql("classpath:integration/product.sql")
  void getBestTest() {}

  @Test
  @Order(4)
  @Sql("classpath:integration/product.sql")
  void getAllTest() {}

  @Test
  @Order(5)
  @Sql("classpath:integration/product.sql")
  void getByIdTest() {}

  @Test
  @Order(6)
  @Sql("classpath:integration/product.sql")
  void createAsProducerTest() {}

  @Test
  @Order(7)
  @Sql("classpath:integration/product.sql")
  void createAsAdminTest() {}

  @Test
  @Order(8)
  @Sql("classpath:integration/product.sql")
  void deleteTest() {}
}
