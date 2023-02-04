package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Product;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest {

  public static final Integer PRODUCT_ID = 0;

  public static final String PRODUCT_NAME = "product-name";

  public static final Double PRODUCT_PRICE = 0.0;

  public static final Integer LEFT_QUANTITY = 1;

  public static final Integer SOLD_QUANTITY = 2;

  public static final String PRODUCER = "producer";

  public static final Integer CATEGORY_ID = 0;

  public static final String IMG_URL = "img-url";

  private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private final ProductService productService = new ProductService(productRepository);

  @Test
  void getByIdTest() {
    mockFound();
    assertEquals(createProduct(), productService.getById(PRODUCT_ID));
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
    assertProductNotFoundException(actualException);
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
    assertProductNotFoundException(actualException);
  }

  private void mockFound() {
    BDDMockito.given(productRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(createProduct()));
  }

  private void mockNotFound() {
    BDDMockito.given(productRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  public static void assertProductNotFoundException(ResponseStatusException actualException) {
    UserServiceTest.assertGenericUserException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }

  public static Product createProduct() {
    return Product.builder()
        .id(PRODUCT_ID)
        .name(PRODUCT_NAME)
        .price(PRODUCT_PRICE)
        .leftQuantity(LEFT_QUANTITY)
        .soldQuantity(SOLD_QUANTITY)
        .producer(PRODUCER)
        .category(CATEGORY_ID)
        .imgUrl(IMG_URL)
        .build();
  }
}
