package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.CategoryController;
import com.musicstore.entity.Category;
import com.musicstore.entity.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class CategoryIntegrationTest {

  private final CategoryController categoryController;

  private static final String FIRST_ADMIN_ID = "mail1@test";

  private static final User FIRST_ADMIN_USER =
      User.builder().mail(FIRST_ADMIN_ID).password("password1").build();
  private static final String CATEGORY_NAME = "category_1";
  private static final String NEW_NAME = "new_name";
  private static final String PRODUCER_ID = "producer_1";

  @Autowired
  public CategoryIntegrationTest(CategoryController categoryController) {
    this.categoryController = categoryController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/category.sql")
  public void getAllTest() {
    ResponseEntity<List<Category>> categoryListResponseEntity = categoryController.getAll();
    List<Category> categoryList = categoryListResponseEntity.getBody();
    assertEquals(1, categoryList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/category.sql")
  public void getByIdTest() {
    ResponseEntity<Category> categoryResponseEntity = categoryController.getById(1);
    Category category = categoryResponseEntity.getBody();
    assertEquals(CATEGORY_NAME, category.getName());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/category.sql")
  public void updateTest() {
    ResponseEntity<Category> categoryResponseEntity =
        categoryController.update(FIRST_ADMIN_USER, buildCategory());
    Category category = categoryResponseEntity.getBody();
    assertEquals(NEW_NAME, category.getName());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/category.sql")
  public void deleteTest() {
    ResponseEntity<Void> responseEntity = categoryController.delete(1, FIRST_ADMIN_USER);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseEntity<List<Category>> categoryListResponseEntity = categoryController.getAll();
    List<Category> categoryList = categoryListResponseEntity.getBody();
    assertEquals(0, categoryList.size());
  }

  @Test
  @Order(5)
  @Sql("classpath:integration/category.sql")
  public void getByProducerTest() {
    ResponseEntity<List<Category>> categoryListResponseEntity =
        categoryController.getByProducer(PRODUCER_ID);
    List<Category> categoryList = categoryListResponseEntity.getBody();
    assertEquals(1, categoryList.size());
  }

  private Category buildCategory() {
    return Category.builder().id(1).name(NEW_NAME).parent(1).imgUrl("img_url1").build();
  }
}
