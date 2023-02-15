package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartServiceTest extends TestUtility {

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
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
  }

  @Test
  void createCartTest() {
    mockProductFound(ProductServiceTest.PRODUCT_ID, PRICE);
    Cart actualCart = cartService.createCart(buildCartRequest());
    actualCart.setDate(DATE);
    Cart expectedCart = buildCart(0);
    expectedCart.setId(null);
    assertEquals(expectedCart, actualCart);
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
              cartService.createCart(buildCartRequest());
            });
    assertReasonException(actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }

  private void mockCartRepository(List<Cart> cartList) {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(cartList);
    BDDMockito.doNothing().when(cartRepository).delete(Mockito.any());
  }

  private void mockProductFound(int productId, Double price) {
    BDDMockito.given(productRepository.findById(productId))
        .willReturn(Optional.of(buildProduct(price)));
  }

  private void mockProductNotFound(int productId) {
    BDDMockito.given(productRepository.findById(productId)).willReturn(Optional.empty());
  }
}
