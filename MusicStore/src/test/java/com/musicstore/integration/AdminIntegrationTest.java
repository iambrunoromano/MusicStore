package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.AdminController;
import com.musicstore.entity.Admin;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@Testcontainers
public class AdminIntegrationTest {

  @Container
  private static MySQLContainer mySQLContainer =
      new MySQLContainer().withUsername("root").withPassword("root").withDatabaseName("musicstore");

  @DynamicPropertySource
  static void dynamicSource(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
    registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
    registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
  }

  private final AdminController adminController;

  private static final String ADMIN_ID = "mail1@test";

  @Autowired
  public AdminIntegrationTest(AdminController adminController) {
    this.adminController = adminController;
  }

  @Test
  @Order(1)
  @Sql("classpath:admin_getall.sql")
  public void adminGetAllTest() {
    Iterable<Admin> adminList = adminController.getAll(ADMIN_ID);
    for (Admin admin : adminList) {
      System.out.println("Admin with ID : " + admin.getMail());
    }
  }
}
