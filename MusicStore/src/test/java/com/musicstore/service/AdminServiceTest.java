package com.musicstore.service;

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

public class AdminServiceTest {

  private static final String DELETE_ID = "delete-id";
  private static final String NAME = "name";
  private static final String SURNAME = "surname";
  private static final String MAIL = "mail";
  private static final String PHONE_NUMBER = "phone-number";

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
    assertNotAdminException(actualException);
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
    UserServiceTest.assertNotAuthenticException(actualException);
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
    assertNotAdminException(actualException);
  }

  @Test
  void isAdminAuthorizedTest() {
    mockAuthenticUser();
    BDDMockito.given(adminRepository.findById(Mockito.anyString()))
        .willReturn(getOptionalAdminBean());
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

  public static void assertNotAdminException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  private Optional<Admin> getOptionalAdminBean() {
    Admin admin = buildAdmin();
    return Optional.of(admin);
  }

  public static Admin buildAdmin() {
    return Admin.builder().mail(MAIL).name(NAME).surname(SURNAME).phoneNumber(PHONE_NUMBER).build();
  }
}
