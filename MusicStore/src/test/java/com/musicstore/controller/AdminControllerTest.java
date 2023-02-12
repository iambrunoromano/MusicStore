package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Admin;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.AdminServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

  public static final String ADMIN_ID = "admin-id";
  public static final User ADMIN_AUTH_USER = User.builder().mail(ADMIN_ID).build();

  private AdminService adminService = Mockito.mock(AdminService.class);
  private AdminController adminController = new AdminController(adminService);

  @Test
  void getAllNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.getAll(ADMIN_AUTH_USER);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getAllAuthorizedTest() {
    List<Admin> adminList = mockAdminList();
    assertEquals(ResponseEntity.ok(adminList), adminController.getAll(ADMIN_AUTH_USER));
  }

  @Test
  void getByIdNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.getById(ADMIN_AUTH_USER, ADMIN_ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getByIdAuthorizedTest() {
    Admin admin = mockAdmin();
    mockGetAdmin();
    assertEquals(ResponseEntity.ok(admin), adminController.getById(ADMIN_AUTH_USER, ADMIN_ID));
  }

  @Test
  void updateNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.save(ADMIN_AUTH_USER, AdminServiceTest.buildAdmin());
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void updateAuthorizedTest() {
    Admin admin = mockAdmin();
    BDDMockito.given(adminService.save(Mockito.any())).willReturn(AdminServiceTest.buildAdmin());
    assertEquals(
        ResponseEntity.ok(admin),
        adminController.save(ADMIN_AUTH_USER, AdminServiceTest.buildAdmin()));
  }

  @Test
  void deleteNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.delete(ADMIN_AUTH_USER, ADMIN_ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  private void mockExceptionThrown() {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willThrow(expectedException);
  }

  private List<Admin> mockAdminList() {
    Admin admin = mockAdmin();
    List<Admin> adminList = new ArrayList<>();
    adminList.add(admin);
    adminList.add(admin);
    BDDMockito.given(adminService.getAll()).willReturn(adminList);
    return adminList;
  }

  private Admin mockAdmin() {
    Admin admin = AdminServiceTest.buildAdmin();
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(admin);
    return admin;
  }

  private void mockGetAdmin() {
    BDDMockito.given(adminService.getAdmin(Mockito.anyString()))
        .willReturn(AdminServiceTest.buildAdmin());
  }
}
