package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.UserController;
import com.musicstore.entity.User;
import com.musicstore.response.UserResponse;
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
    ResponseEntity<List<UserResponse>> userResponseListResponseEntity =
        userController.getAll(FIRST_ADMIN_ID);
    List<UserResponse> userResponseList = userResponseListResponseEntity.getBody();
    assertEquals(2, userResponseList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/user.sql")
  void getByIdTest() {
    ResponseEntity<UserResponse> userResponseEntity =
        userController.getById(buildUser(FIRST_USER_ID, FIRST_USER_PASSWORD));
    UserResponse userResponse = userResponseEntity.getBody();
    assertEquals(FIRST_USER_ID, userResponse.getMail());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/user.sql")
  void saveTest() {
    ResponseEntity<UserResponse> userResponseEntity =
        userController.save(buildUser(THIRD_USER_ID, SECOND_USER_PASSWORD));
    UserResponse userResponse = userResponseEntity.getBody();
    assertEquals(THIRD_USER_ID, userResponse.getMail());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/user.sql")
  void deleteTest() {
    userController.delete(buildUser(FIRST_USER_ID, FIRST_USER_PASSWORD));
    ResponseEntity<List<UserResponse>> userResponseListResponseEntity =
        userController.getAll(FIRST_ADMIN_ID);
    List<UserResponse> userResponseList = userResponseListResponseEntity.getBody();
    assertEquals(1, userResponseList.size());
    UserResponse leftUserResponse = userResponseList.get(0);
    assertEquals(SECOND_USER_ID, leftUserResponse.getMail());
  }

  public static User buildUser(String userId, String userPassword) {
    return User.builder().mail(userId).password(userPassword).build();
  }
}
