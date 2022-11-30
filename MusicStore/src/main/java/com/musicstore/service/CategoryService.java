package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Category;
import com.musicstore.repository.CategoryRepository;
import com.musicstore.repository.ProducerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(
      CategoryRepository categoryRepository, ProducerRepository producerRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Iterable<Category> getAll() {
    return categoryRepository.findAll();
  }

  public Optional<Category> getById(int id) {
    return categoryRepository.findById(id);
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public boolean delete(int id) {
    Optional<Category> optionalCategory = this.getById(id);
    if (optionalCategory.isPresent()) {
      log.info("Deleting category with id [{}]", id);
      categoryRepository.delete(optionalCategory.get());
      return true;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
  }

  public List<Category> getAllByProducer(String mail) {
    // TODO: implement this method once product track is on. Guide:
    // 1. Query all products with product.producer = mail (launch exception if mail is not producer)
    // 2. Select distinct categories from all those products (launch exception if any product is
    // found for that producer)
    // 3. Query those distinct categories on categories table and give back to the caller (launch
    // exception if no category is found for products)
    return new ArrayList<>();
  }
}
