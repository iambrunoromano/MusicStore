package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.User;
import com.musicstore.response.UserResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.AdminServiceTest;
import com.musicstore.service.UserService;
import com.musicstore.service.UserServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

  public static final String NOT_ADMIN_ID = "not-admin-id";
  public static final String ADMIN_ID = "admin-id";

  private AdminService adminService = Mockito.mock(AdminService.class);
  private UserService userService = Mockito.mock(UserService.class);
  private UserController userController = new UserController(adminService, userService);

  @Test
  void getAllNotAdminTest() {
    mockIsNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userController.getAll(NOT_ADMIN_ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockUserList();
    ResponseEntity<List<UserResponse>> userListResponseEntity = userController.getAll(ADMIN_ID);
    List<UserResponse> userResponseList = userListResponseEntity.getBody();
    assertEquals(buildUserResponseList(), userResponseList);
  }

  @Test
  void getByIdTest() {
    mockIsAuthentic();
    ResponseEntity<UserResponse> userResponseEntity =
        userController.getById(UserServiceTest.buildUser());
    UserResponse userResponse = userResponseEntity.getBody();
    assertEquals(UserServiceTest.buildUserResponse(), userResponse);
  }

  @Test
  void getByIdNotAuthenticTest() {
    mockIsNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userController.getById(UserServiceTest.buildUser());
            });
    UserServiceTest.assertNotAuthenticException(actualException);
  }

  @Test
  void saveTest() {
    mockSave();
    ResponseEntity<UserResponse> userResponseEntity =
        userController.save(UserServiceTest.buildUser());
    UserResponse userResponse = userResponseEntity.getBody();
    assertEquals(UserServiceTest.buildUserResponse(), userResponse);
  }

  @Test
  void deleteNotUserTest() {
    mockIsNotUser();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userController.delete(UserServiceTest.buildUser());
            });
    UserServiceTest.assertNotUserException(actualException);
  }

  private void mockIsNotUser() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willThrow(expectedException);
  }

  private void mockIsNotAdmin() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    BDDMockito.given(adminService.isAdmin(Mockito.anyString())).willThrow(expectedException);
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockUserList() {
    List<User> userList = buildUserList();
    BDDMockito.given(userService.getAll()).willReturn(userList);
  }

  private List<User> buildUserList() {
    List<User> userList = new ArrayList<>();
    userList.add(UserServiceTest.buildUser());
    userList.add(UserServiceTest.buildUser());
    return userList;
  }

  private List<UserResponse> buildUserResponseList() {
    List<UserResponse> userResponseList = new ArrayList<>();
    userResponseList.add(UserServiceTest.buildUserResponse());
    userResponseList.add(UserServiceTest.buildUserResponse());
    return userResponseList;
  }

  private void mockIsAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willReturn(UserServiceTest.buildUser());
  }

  private void mockIsNotAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC));
  }

  private void mockSave() {
    BDDMockito.given(userService.save(Mockito.any())).willReturn(UserServiceTest.buildUser());
  }
}
