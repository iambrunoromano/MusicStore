package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Category;
import com.musicstore.repository.CategoryRepository;
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
public class CategoryService {
  // TODO: category logic that was in store procedure put into category service

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Autowired
  public CategoryService(
      CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  public Optional<Category> getById(int id) {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (!optionalCategory.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
    }
    return optionalCategory;
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public boolean delete(int id) {
    Optional<Category> optionalCategory = this.getById(id);
    categoryRepository.delete(optionalCategory.get());
    return true;
  }

  public List<Category> getByProducer(String mail) {
    List<Category> categoryList = categoryRepository.findCategoriesByProducer(mail);
    if (categoryList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
    }
    return categoryList;
  }
}
