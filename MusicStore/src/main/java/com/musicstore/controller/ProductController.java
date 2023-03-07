package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.request.UserRequest;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProductService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
  public ResponseEntity<List<Product>> getByProducer(@PathVariable @Email @NotBlank String mail) {
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
      @RequestHeader UserRequest userRequest, @RequestBody Product product) {
    userService.isAuthentic(userRequest);
    producerService.isProducer(userRequest.getMail());
    return ResponseEntity.ok(productService.save(product));
  }

  @PostMapping(value = "/admin")
  public ResponseEntity<Product> createAsAdmin(
      @RequestHeader UserRequest userRequest, @RequestBody Product product) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(productService.save(product));
  }

  @DeleteMapping(value = "/{product-id}")
  public ResponseEntity<Void> delete(
      @RequestHeader UserRequest userRequest, @PathVariable int productId) {
    // TODO: check that all the non-post calls have no @RequestBody and change
    userService.isAuthentic(userRequest);
    producerService.isProducer(userRequest.getMail());
    Product product = productService.getById(productId);
    if (!userRequest.getMail().equals(product.getProducer())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
    }
    productService.delete(productId);
    return ResponseEntity.ok().build();
  }
}
