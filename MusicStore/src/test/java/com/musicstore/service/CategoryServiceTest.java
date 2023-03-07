package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Category;
import com.musicstore.repository.CategoryRepository;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest extends TestUtility {

  private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
  private final CategoryService categoryService = new CategoryService(categoryRepository);

  @Test
  void deleteFoundTest() {
    mockFindById();
    assertTrue(categoryService.delete(ID));
  }

  @Test
  void deleteNotFoundTest() {
    mockFindByIdNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              categoryService.delete(ID);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
  }

  @Test
  void getByProducerTest() {
    mockFindCategoryByProducer();
    mockFindById();
    List<Category> categoryList = categoryService.getByProducer(PRODUCER);
    assertEquals(1, categoryList.size());
    assertCategory(categoryList.get(0));
  }

  @Test
  void getByProducerNotFoundTest() {
    mockFindCategoryByProducerNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              categoryService.getByProducer(PRODUCER);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
  }

  private void mockFindCategoryByProducerNotFound() {
    BDDMockito.given(categoryRepository.findCategoriesByProducer(Mockito.anyString()))
        .willReturn(new ArrayList<>());
  }

  private void mockFindCategoryByProducer() {
    List<Category> categoryList = new ArrayList<>();
    categoryList.add(buildCategory());
    BDDMockito.given(categoryRepository.findCategoriesByProducer(Mockito.anyString()))
        .willReturn(categoryList);
  }

  private void mockFindByIdNotFound() {
    BDDMockito.given(categoryRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void mockFindById() {
    BDDMockito.given(categoryRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(buildCategory()));
  }
}
