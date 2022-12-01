package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

  // TODO: unit tests

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Iterable<Product> getAll() {
    return productRepository.findAll();
  }

  public Optional<Product> getById(int id) {
    return productRepository.findById(id);
  }

  public Product save(Product p) {
    return productRepository.save(p);
  }

  public boolean delete(int id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isPresent()) {
      log.info("Deleting product with id [{}]", id);
      productRepository.delete(optionalProduct.get());
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }

  public List<Product> getByProducer(String mail) {
    return productRepository.findByProducer(mail);
  }

  public List<Product> getByCategory(Integer category) {
    return productRepository.findByCategory(category);
  }

  public List<Product> getMostSold() {
    return productRepository.findByOrderBySoldQuantityDesc();
  }
}
