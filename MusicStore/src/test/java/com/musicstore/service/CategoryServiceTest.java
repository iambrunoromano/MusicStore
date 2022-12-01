package com.musicstore.service;

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

class CategoryServiceTest {

  private static final Integer ID = 1;
  private static final String NAME = "category-name";
  private static final Integer PARENT = 0;
  private static final String IMG_URL = "img-url";
  private static final String PRODUCER = "producer";

  private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
  private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private final CategoryService categoryService =
      new CategoryService(categoryRepository, productRepository);

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
    assertCategoryNotFoundException(actualException);
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
    assertCategoryNotFoundException(actualException);
  }

  public static void assertCategory(Category expectedCategory) {
    assertEquals(expectedCategory.getId(), ID);
    assertEquals(expectedCategory.getName(), NAME);
    assertEquals(expectedCategory.getParent(), PARENT);
    assertEquals(expectedCategory.getImgUrl(), IMG_URL);
  }

  public static void assertCategoryNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CATEGORY_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  private void mockFindCategoryByProducerNotFound() {
    BDDMockito.given(productRepository.findCategoriesByProducer(Mockito.anyString()))
        .willReturn(new ArrayList<>());
  }

  private void mockFindCategoryByProducer() {
    List<Integer> categoryIdList = new ArrayList<>();
    categoryIdList.add(ID);
    BDDMockito.given(productRepository.findCategoriesByProducer(Mockito.anyString()))
        .willReturn(categoryIdList);
  }

  private void mockFindByIdNotFound() {
    BDDMockito.given(categoryRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void mockFindById() {
    BDDMockito.given(categoryRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(buildCategory()));
  }

  private Category buildCategory() {
    return Category.builder().id(ID).name(NAME).parent(PARENT).imgUrl(IMG_URL).build();
  }
}
