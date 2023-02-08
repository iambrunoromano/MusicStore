package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.AdminController;
import com.musicstore.entity.Admin;
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

  private static final String ADMIN_ID = "mail1@test";

  @Autowired
  public AdminIntegrationTest(AdminController adminController) {
    this.adminController = adminController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/admin/admin_getall.sql")
  public void adminGetAllTest() {
    List<Admin> adminList = adminController.getAll(ADMIN_ID);
    assertEquals(2, adminList.size());
  }
}
