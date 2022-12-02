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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Autowired
  public CategoryService(
      CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  public Iterable<Category> getAll() {
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
    List<Integer> categoryIdList = productRepository.findCategoriesByProducer(mail);
    if (categoryIdList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
    }
    List<Category> categoryList = new ArrayList<>();
    for (Integer categoryId : categoryIdList) {
      Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
      if (!optionalCategory.isPresent()) {
        log.error(
            "Category with id [{}] of product belonging to producer with mail [{}] not found",
            categoryId,
            mail);
      } else {
        categoryList.add(optionalCategory.get());
      }
    }
    return categoryList;
  }
}
