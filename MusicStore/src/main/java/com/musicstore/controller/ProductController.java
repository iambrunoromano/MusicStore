package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO: all request parameters need to be not nullable/empty and validated in all controllers

@RestController
@Slf4j
@RequestMapping(value = "product")
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
  public List<Product> getByCategory(@PathVariable int categoryId) {
    return productService.getByCategory(categoryId);
  }

  @GetMapping(value = "/producer/{mail}")
  public List<Product> getByProducer(@PathVariable String mail) {
    return productService.getByProducer(mail);
  }

  @GetMapping(value = "/best")
  public List<Product> getBest() {
    return productService.getMostSold();
  }

  @GetMapping
  public Iterable<Product> getAll() {
    return productService.getAll();
  }

  @GetMapping(value = "/product/{product-id}")
  public Product getById(@PathVariable int productId) {
    return productService.getById(productId);
  }

  @PostMapping(value = "/producer/{mail}")
  public Product createAsProducer(@PathVariable String mail, @RequestBody Product product) {
    producerService.isProducer(mail);
    return productService.save(product);
  }

  @PostMapping(value = "/admin/{mail}")
  public Product createAsAdmin(@PathVariable String mail, @RequestBody Product product) {
    adminService.isAdmin(mail);
    return productService.save(product);
  }

  @DeleteMapping(value = "/producer/{mail}")
  public void delete(@PathVariable String mail, @RequestBody int productId) {
    producerService.isProducer(mail);
    Product product = productService.getById(productId);
    if (mail != null && mail.equals(product.getProducer())) {
      productService.delete(productId);
    }
    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_PRODUCER_MISMATCH);
  }
}
