package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Admin;
import com.musicstore.entity.User;
import com.musicstore.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminServiceTest extends TestUtility {
  private AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
  private UserService userService = Mockito.mock(UserService.class);
  private AdminService adminService;

  public AdminServiceTest() {
    adminService = new AdminService(adminRepository, userService);
  }

  @Test
  void deleteNotAuthorizedTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminService.delete(DELETE_ID);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void isAdminNotAuthenticTest() {
    mockNotAuthenticUser();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminService.isAdmin(User.builder().mail(MAIL).build());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
    ;
  }

  @Test
  void isAdminNotAuthorizedTest() {
    mockAuthenticUser();
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminService.isAdmin(User.builder().mail(MAIL).build());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void isAdminAuthorizedTest() {
    mockAuthenticUser();
    BDDMockito.given(adminRepository.findById(Mockito.anyString()))
        .willReturn(buildOptionalAdmin());
    Admin actualAdmin = adminService.isAdmin(User.builder().mail(MAIL).build());
    assertEquals(buildAdmin(), actualAdmin);
  }

  private void mockNotFound() {
    BDDMockito.given(adminRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
  }

  private void mockAuthenticUser() {
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willReturn(new User());
  }

  private void mockNotAuthenticUser() {
    BDDMockito.given(userService.isAuthentic(Mockito.any()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC));
  }
}
