package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO: all request parameters need to be not nullable/empty and validated in all controllers

@RestController
@Slf4j
@RequestMapping(value = "products")
public class ProductController {

  private final AdminService adminService;
  private final ProducerService producerService;
  private final ProductService productService;

  @Autowired
  public ProductController(
      AdminService adminService, ProducerService producerService, ProductService productService) {
    this.adminService = adminService;
    this.producerService = producerService;
    this.productService = productService;
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

  @PostMapping(value = "/producer/{mail}")
  public ResponseEntity<Product> createAsProducer(
      @PathVariable String mail, @RequestBody Product product) {
    producerService.isProducer(mail);
    return ResponseEntity.ok(productService.save(product));
  }

  @PostMapping(value = "/admin")
  public ResponseEntity<Product> createAsAdmin(
          @RequestHeader User user, @RequestBody Product product) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(productService.save(product));
  }

  @DeleteMapping(value = "/producer/{mail}")
  public ResponseEntity<Void> delete(@PathVariable String mail, @RequestBody int productId) {
    producerService.isProducer(mail);
    Product product = productService.getById(productId);
    if (mail == null || !mail.equals(product.getProducer())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
    }
    productService.delete(productId);
    return ResponseEntity.ok().build();
  }
}
