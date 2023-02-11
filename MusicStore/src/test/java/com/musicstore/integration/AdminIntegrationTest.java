package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.AdminController;
import com.musicstore.entity.Admin;
import com.musicstore.service.AdminServiceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class AdminIntegrationTest {

  private final AdminController adminController;

  private static final String FIRST_ADMIN_ID = "mail1@test";

  private static final String SECOND_ADMIN_ID = "mail2@test";

  @Autowired
  public AdminIntegrationTest(AdminController adminController) {
    this.adminController = adminController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/admin.sql")
  public void getAllTest() {
    List<Admin> adminList = adminController.getAll(FIRST_ADMIN_ID);
    assertEquals(2, adminList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/admin.sql")
  public void getByIdTest() {
    Admin admin = adminController.getById(FIRST_ADMIN_ID, FIRST_ADMIN_ID);
    assertEquals(FIRST_ADMIN_ID, admin.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/admin.sql")
  public void updateTest() {
    Admin adminToPost = AdminServiceTest.buildAdmin();
    adminToPost.setMail(FIRST_ADMIN_ID);
    Admin saveAdmin = adminController.save(FIRST_ADMIN_ID, adminToPost);
    saveAdmin.setInsertDate(null);
    saveAdmin.setUpdateDate(null);
    assertEquals(adminToPost, saveAdmin);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/admin.sql")
  public void deleteTest() {
    adminController.delete(SECOND_ADMIN_ID,FIRST_ADMIN_ID);
    List<Admin> adminList = adminController.getAll(SECOND_ADMIN_ID);
    assertEquals(1, adminList.size());
    Admin leftAdmin = adminList.get(0);
    assertEquals(SECOND_ADMIN_ID, leftAdmin.getMail());
  }
}
