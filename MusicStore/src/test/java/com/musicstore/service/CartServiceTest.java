package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.Product;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.ProductRepository;
import com.musicstore.request.CartRequest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartServiceTest {

  private static final int QUANTITY = 2;
  private static final Timestamp DATE = Timestamp.from(Instant.now());
  private static final Double PRICE = 1.0;
  private static final Double OVERALL_PRICE = 2.0;

  private CartRepository cartRepository = Mockito.mock(CartRepository.class);
  private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private CartService cartService;

  public CartServiceTest() {
    cartService = new CartService(cartRepository, productRepository);
  }

  @Test
  void deleteNotFound() {
    mockCartRepository(new ArrayList<>());
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartService.deleteByMail("not_found_mail");
            });
    assertCartNotFoundException(actualException);
  }

  @Test
  void createCartTest() {
    mockProductFound(ProductServiceTest.PRODUCT_ID, PRICE);
    assertEquals(createCart(), cartService.createCart(createCartRequest()));
  }

  @Test
  void createCartAbsentProductTest() {
    mockProductNotFound(ProductServiceTest.PRODUCT_ID);
    doCreateCartExceptionTest();
  }

  @Test
  void createCartProductPriceNotDefinedTest() {
    mockProductFound(ProductServiceTest.PRODUCT_ID, null);
    doCreateCartExceptionTest();
  }

  private void doCreateCartExceptionTest() {
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              cartService.createCart(createCartRequest());
            });
    assertProductNotFoundException(actualException);
  }

  private void mockCartRepository(List<Cart> cartList) {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(cartList);
    BDDMockito.doNothing().when(cartRepository).delete(Mockito.any());
  }

  private void mockProductFound(int productId, Double price) {
    BDDMockito.given(productRepository.findById(productId))
        .willReturn(Optional.of(createProduct(price)));
  }

  private void mockProductNotFound(int productId) {
    BDDMockito.given(productRepository.findById(productId)).willReturn(Optional.empty());
  }

  public static void assertCartNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  public static void assertProductNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  // TODO: move all constants and createSomething() methods under a test utility class

  private CartRequest createCartRequest() {
    return CartRequest.builder()
        .productId(ProductServiceTest.PRODUCT_ID)
        .quantity(QUANTITY)
        .mail(UserServiceTest.MAIL)
        .date(DATE)
        .build();
  }

  private Product createProduct(Double price) {
    return Product.builder().id(ProductServiceTest.PRODUCT_ID).price(price).build();
  }

  private Cart createCart() {
    return Cart.builder()
        .productId(ProductServiceTest.PRODUCT_ID)
        .quantity(QUANTITY)
        .mail(UserServiceTest.MAIL)
        .date(DATE)
        .bought(false)
        .overallPrice(OVERALL_PRICE)
        .build();
  }
}
