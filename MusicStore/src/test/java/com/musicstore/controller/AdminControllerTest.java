package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Admin;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminControllerTest extends TestUtility {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private AdminController adminController = new AdminController(adminService);

  @Test
  void getAllNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.getAll(FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getAllAuthorizedTest() {
    List<Admin> adminList = mockAdminList();
    assertEquals(ResponseEntity.ok(adminList), adminController.getAll(FIRST_ADMIN_USER));
  }

  @Test
  void getByIdNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.getById(FIRST_ADMIN_USER, MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getByIdAuthorizedTest() {
    Admin admin = mockAdmin();
    mockGetAdmin();
    assertEquals(ResponseEntity.ok(admin), adminController.getById(FIRST_ADMIN_USER, MAIL));
  }

  @Test
  void updateNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.save(FIRST_ADMIN_USER, AdminServiceTest.buildAdmin());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void updateAuthorizedTest() {
    Admin admin = mockAdmin();
    BDDMockito.given(adminService.save(Mockito.any())).willReturn(AdminServiceTest.buildAdmin());
    assertEquals(
        ResponseEntity.ok(admin),
        adminController.save(FIRST_ADMIN_USER, AdminServiceTest.buildAdmin()));
  }

  @Test
  void deleteNotAuthorizedTest() {
    mockExceptionThrown();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminController.delete(FIRST_ADMIN_USER, MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
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
