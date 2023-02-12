package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Category;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.AdminServiceTest;
import com.musicstore.service.CategoryService;
import com.musicstore.service.CategoryServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

  // TODO: collect all the constants used for testing under a UtilityTest class

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final CategoryService categoryService = Mockito.mock(CategoryService.class);

  private CategoryController categoryController =
      new CategoryController(adminService, categoryService);

  @Test
  void getByIdFoundTest() {
    mockFound();
    ResponseEntity<Category> actualCategory = categoryController.getById(CategoryServiceTest.ID);
    CategoryServiceTest.assertCategory(actualCategory.getBody());
  }

  @Test
  void getByIdNotFoundTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              categoryController.getById(CategoryServiceTest.ID);
            });
    CategoryServiceTest.assertCategoryNotFoundException(actualException);
  }

  @Test
  void updateByAdminTest() {
    mockIsAdmin();
    mockSave();
    ResponseEntity<Category> actualCategory =
        categoryController.update(
            AdminControllerTest.ADMIN_ID, CategoryServiceTest.buildCategory());
    CategoryServiceTest.assertCategory(actualCategory.getBody());
  }

  @Test
  void updateByNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              categoryController.update(AdminControllerTest.ADMIN_ID, new Category());
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void deleteByNotAdminTest() {
    mockNotAdmin();
    User user = User.builder().mail("mail").build();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              categoryController.delete(CategoryServiceTest.ID, user);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  private void mockFound() {
    BDDMockito.given(categoryService.getById(Mockito.anyInt()))
        .willReturn(Optional.of(CategoryServiceTest.buildCategory()));
  }

  private void mockNotFound() {
    BDDMockito.given(categoryService.getById(Mockito.anyInt()))
        .willThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND));
  }

  private void mockSave() {
    BDDMockito.given(categoryService.save(Mockito.any()))
        .willReturn(CategoryServiceTest.buildCategory());
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }
}
