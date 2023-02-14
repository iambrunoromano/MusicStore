package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest extends TestUtility {

  private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private final ProductService productService = new ProductService(productRepository);

  @Test
  void getByIdTest() {
    mockFound();
    assertEquals(buildProduct(), productService.getById(PRODUCT_ID));
  }

  @Test
  void getByIdNotFoundTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productService.getById(PRODUCT_ID);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }

  @Test
  void deleteNotFoundTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productService.delete(PRODUCT_ID);
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }

  private void mockFound() {
    BDDMockito.given(productRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(buildProduct()));
  }

  private void mockNotFound() {
    BDDMockito.given(productRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }
}
