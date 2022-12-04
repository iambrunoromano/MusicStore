package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

  private static final Integer PRODUCT_ID = 0;

  private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private final ProductService productService = new ProductService(productRepository);

  @Test
  void deleteNotFoundTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              productService.delete(PRODUCT_ID);
            });
    assertProductNotFoundException(actualException);
  }

  private void mockNotFound() {
    BDDMockito.given(productRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void assertProductNotFoundException(ResponseStatusException actualException) {
    UserServiceTest.assertGenericUserException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }
}
