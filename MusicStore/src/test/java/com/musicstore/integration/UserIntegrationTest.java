package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.UserController;
import com.musicstore.entity.User;
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
public class UserIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  public static final String FIRST_USER_ID = "usermail1@test";
  public static final String FIRST_USER_PASSWORD = "password1";
  public static final String SECOND_USER_ID = "usermail2@test";
  public static final String SECOND_USER_PASSWORD = "password2";
  private static final String THIRD_USER_ID = "usermail3@test";

  private final UserController userController;

  @Autowired
  public UserIntegrationTest(UserController userController) {
    this.userController = userController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/user.sql")
  void getAllTest() {
    List<User> userList = userController.getAll(FIRST_ADMIN_ID);
    assertEquals(2, userList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/user.sql")
  void getByIdTest() {
    User user = userController.getById(buildUser(FIRST_USER_ID, FIRST_USER_PASSWORD));
    assertEquals(FIRST_USER_ID, user.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/user.sql")
  void saveTest() {
    User user = userController.save(buildUser(THIRD_USER_ID, SECOND_USER_PASSWORD));
    assertEquals(THIRD_USER_ID, user.getMail());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/user.sql")
  void deleteTest() {
    userController.delete(buildUser(FIRST_USER_ID, FIRST_USER_PASSWORD));
    List<User> userList = userController.getAll(FIRST_ADMIN_ID);
    assertEquals(1, userList.size());
    User leftUser = userList.get(0);
    assertEquals(SECOND_USER_ID, leftUser.getMail());
  }

  public static User buildUser(String userId, String userPassword) {
    return User.builder().mail(userId).password(userPassword).build();
  }
}
