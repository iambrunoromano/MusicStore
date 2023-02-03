package com.musicstore.controller;

import com.musicstore.model.Product;
import com.musicstore.model.WebUser;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProductService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProductRestController {

  @Autowired private AdminService adminService;

  @Autowired private ProducerService producerService;

  @Autowired private UserService webuserService;

  @Autowired private ProductService productService;

  public ProductRestController() {}

  @RequestMapping("/musicstore/api/product/category/{id}")
  public List<ProductBean> ProductsByCategory(@PathVariable int id) {
    return productService.ProductsByCategory(id);
  }

  @RequestMapping("/musicstore/api/product/{mail}/products")
  public List<ProductBean> ProductsByProducer(@PathVariable String mail) {
    return productService.ProductsByProducer(mail);
  }

  @RequestMapping("/musicstore/api/product/best")
  public List<ProductBean> BestProducts() {
    return productService.BestProducts();
  }

  @RequestMapping("/musicstore/api/product/all")
  public Iterable<ProductBean> getAll() {
    return productService.getAll();
  }

  @RequestMapping("/musicstore/api/product/{id}")
  public ProductBean getById(@PathVariable int id) {
    Optional<ProductBean> product = productService.getById(id);
    if (!product.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
    return product.get();
  }

  @RequestMapping(value = "/musicstore/api/product", method = RequestMethod.POST)
  public ProductBean create(@RequestBody Map<String, Map<String, String>> map) {
    ProductBean pb = Utility.productDeMap(map.get("topost"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    if (!adminService.isAdmin(b) && !pb.getProducer().equals(b.getMail())
        || !webuserService.isWebUser(b)) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
    }
    return productService.create(pb);
  }

  @RequestMapping(value = "/musicstore/api/product/{id}", method = RequestMethod.PUT)
  public ProductBean update(
      @PathVariable int id, @RequestBody Map<String, Map<String, String>> map) {
    ProductBean pb = Utility.productDeMap(map.get("topost"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    if (!adminService.isAdmin(b) && !pb.getProducer().equals(b.getMail())
        || !webuserService.isWebUser(b)) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
    }
    Optional<ProductBean> updatedProduct = productService.update(id, pb);
    if (!updatedProduct.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
    return updatedProduct.get();
  }

  @RequestMapping(value = "/musicstore/api/product/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
    if (!adminService.isAdmin(b) && !getById(id).getProducer().equals(b.getMail())
        || !webuserService.isWebUser(b)) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
    }
    Boolean isDeleted = productService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
  }
}
