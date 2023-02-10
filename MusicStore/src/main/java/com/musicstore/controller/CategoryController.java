package com.musicstore.controller;

import com.musicstore.entity.Category;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: external test
// TODO: Returned Response Entity HTTP Status
// TODO: Do refactor of naming for ids of all entities across all repo

@RestController
@Slf4j
@RequestMapping(value = "category")
public class CategoryController {

  private final AdminService adminService;

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(AdminService adminService, CategoryService categoryService) {
    this.adminService = adminService;
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/all")
  public List<Category> getAll() {
    return categoryService.getAll();
  }

  @GetMapping(value = "/{category-id}")
  public Category getById(@PathVariable int categoryId) {
    Optional<Category> category = categoryService.getById(categoryId);
    return category.get();
  }

  @PostMapping(value = "/{admin-id}")
  public Category update(@PathVariable String adminId, @RequestBody Category category) {
    adminService.isAdmin(adminId);
    return categoryService.save(category);
  }

  @DeleteMapping(value = "/{category-id}")
  public void delete(@PathVariable int categoryId, @RequestBody User user) {
    adminService.isAdmin(user.getMail());
    categoryService.delete(categoryId);
  }

  @GetMapping("/{producer-id}/categories")
  public List<Category> getByProducer(@PathVariable String producerId) {
    return categoryService.getByProducer(producerId);
  }
}
