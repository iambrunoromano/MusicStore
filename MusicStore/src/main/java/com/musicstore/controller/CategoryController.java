package com.musicstore.controller;

import com.musicstore.entity.Category;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: Do refactor of naming for ids of all entities across all repo

@RestController
@Slf4j
@RequestMapping(value = "categories")
public class CategoryController {

  private final AdminService adminService;

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(AdminService adminService, CategoryService categoryService) {
    this.adminService = adminService;
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Category>> getAll() {
    return ResponseEntity.ok(categoryService.getAll());
  }

  @GetMapping(value = "/{category-id}")
  public ResponseEntity<Category> getById(@PathVariable int categoryId) {
    Optional<Category> category = categoryService.getById(categoryId);
    return ResponseEntity.ok(category.get());
  }

  @PostMapping
  public ResponseEntity<Category> update(
          @RequestHeader User user, @RequestBody Category category) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(categoryService.save(category));
  }

  @DeleteMapping(value = "/{category-id}")
  public ResponseEntity<Void> delete(@PathVariable int categoryId, @RequestHeader User user) {
    adminService.isAdmin(user);
    categoryService.delete(categoryId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{producer-id}/categories")
  public ResponseEntity<List<Category>> getByProducer(@PathVariable String producerId) {
    return ResponseEntity.ok(categoryService.getByProducer(producerId));
  }
}
