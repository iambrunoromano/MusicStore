package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.AdminController;
import com.musicstore.entity.Admin;
import com.musicstore.entity.User;
import com.musicstore.service.AdminServiceTest;
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
public class AdminIntegrationTest {

  private final AdminController adminController;

  private static final User FIRST_ADMIN_USER =
      User.builder().mail("mail1@test").password("password1").build();

  private static final User SECOND_ADMIN_USER =
      User.builder().mail("mail2@test").password("password2").build();

  @Autowired
  public AdminIntegrationTest(AdminController adminController) {
    this.adminController = adminController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/admin.sql")
  public void getAllTest() {
    ResponseEntity<List<Admin>> responseEntityAdminList = adminController.getAll(FIRST_ADMIN_USER);
    List<Admin> allAdminList = responseEntityAdminList.getBody();
    assertEquals(2, allAdminList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/admin.sql")
  public void getByIdTest() {
    ResponseEntity<Admin> adminResponseEntity =
        adminController.getById(FIRST_ADMIN_USER, FIRST_ADMIN_USER.getMail());
    Admin foundAdmin = adminResponseEntity.getBody();
    assertEquals(FIRST_ADMIN_USER.getMail(), foundAdmin.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/admin.sql")
  public void updateTest() {
    Admin adminToPost = AdminServiceTest.buildAdmin();
    adminToPost.setMail(FIRST_ADMIN_USER.getMail());
    ResponseEntity<Admin> adminResponseEntity = adminController.save(FIRST_ADMIN_USER, adminToPost);
    Admin savedAdmin = adminResponseEntity.getBody();
    savedAdmin.setInsertDate(null);
    savedAdmin.setUpdateDate(null);
    assertEquals(adminToPost, savedAdmin);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/admin.sql")
  public void deleteTest() {
    ResponseEntity<Void> responseEntity =
        adminController.delete(SECOND_ADMIN_USER, FIRST_ADMIN_USER.getMail());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseEntity<List<Admin>> responseEntityAdminList = adminController.getAll(SECOND_ADMIN_USER);
    List<Admin> allAdminList = responseEntityAdminList.getBody();
    assertEquals(1, allAdminList.size());
    Admin leftAdmin = allAdminList.get(0);
    assertEquals(SECOND_ADMIN_USER.getMail(), leftAdmin.getMail());
  }
}
