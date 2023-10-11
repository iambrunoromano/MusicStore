package com.musicstore.controller;

import com.musicstore.entity.Category;
import com.musicstore.request.UserRequest;
import com.musicstore.service.AdminService;
import com.musicstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
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

  @GetMapping(value = "/{categoryId}")
  public ResponseEntity<Category> getById(@PathVariable int categoryId) {
    Optional<Category> category = categoryService.getById(categoryId);
    return ResponseEntity.ok(category.get());
  }

  @PostMapping
  public ResponseEntity<Category> update(
      @RequestHeader UserRequest userRequest, @RequestBody Category category) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(categoryService.save(category));
  }

  @DeleteMapping(value = "/{categoryId}")
  public ResponseEntity<Void> delete(
      @PathVariable int categoryId, @RequestHeader UserRequest userRequest) {
    adminService.isAdmin(userRequest);
    categoryService.delete(categoryId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{producerId}/categories")
  public ResponseEntity<List<Category>> getByProducer(@PathVariable @Email @NotBlank String producerId) {
    return ResponseEntity.ok(categoryService.getByProducer(producerId));
  }
}
