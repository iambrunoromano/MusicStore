package com.musicstore.controller;

import com.musicstore.TestUtility;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest extends TestUtility {

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
              userController.getAll(FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockUserList();
    ResponseEntity<List<UserResponse>> userListResponseEntity =
        userController.getAll(FIRST_ADMIN_USER);
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
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
    ;
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
    assertReasonException(actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  private void mockIsNotUser() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willThrow(expectedException);
  }

  private void mockIsNotAdmin() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willThrow(expectedException);
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockUserList() {
    List<User> userList = buildUserList();
    BDDMockito.given(userService.getAll()).willReturn(userList);
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
