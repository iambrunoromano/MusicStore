package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProductService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO: all request parameters need to be not nullable/empty and validated in all controllers

@RestController
@RequestMapping(value = "products")
public class ProductController {

  private final AdminService adminService;
  private final ProducerService producerService;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public ProductController(
      AdminService adminService,
      ProducerService producerService,
      ProductService productService,
      UserService userService) {
    this.adminService = adminService;
    this.producerService = producerService;
    this.productService = productService;
    this.userService = userService;
  }

  @GetMapping(value = "/category/{category-id}")
  public ResponseEntity<List<Product>> getByCategory(@PathVariable int categoryId) {
    return ResponseEntity.ok(productService.getByCategory(categoryId));
  }

  @GetMapping(value = "/producer/{mail}")
  public ResponseEntity<List<Product>> getByProducer(@PathVariable String mail) {
    return ResponseEntity.ok(productService.getByProducer(mail));
  }

  @GetMapping(value = "/best")
  public ResponseEntity<List<Product>> getBest() {
    return ResponseEntity.ok(productService.getMostSold());
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(productService.getAll());
  }

  @GetMapping(value = "/product/{product-id}")
  public ResponseEntity<Product> getById(@PathVariable int productId) {
    return ResponseEntity.ok(productService.getById(productId));
  }

  @PostMapping(value = "/producer")
  public ResponseEntity<Product> createAsProducer(
      @RequestHeader User user, @RequestBody Product product) {
    userService.isAuthentic(user);
    producerService.isProducer(user.getMail());
    return ResponseEntity.ok(productService.save(product));
  }

  @PostMapping(value = "/admin")
  public ResponseEntity<Product> createAsAdmin(
      @RequestHeader User user, @RequestBody Product product) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(productService.save(product));
  }

  @DeleteMapping(value = "/{product-id}")
  public ResponseEntity<Void> delete(@RequestHeader User user, @PathVariable int productId) {
    // TODO: check that all the non-post calls have no @RequestBody and change
    userService.isAuthentic(user);
    producerService.isProducer(user.getMail());
    Product product = productService.getById(productId);
    if (!user.getMail().equals(product.getProducer())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
    }
    productService.delete(productId);
    return ResponseEntity.ok().build();
  }
}
