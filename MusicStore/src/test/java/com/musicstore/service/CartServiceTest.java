package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartServiceTest {
  private CartRepository cartRepository = Mockito.mock(CartRepository.class);
  private CartService cartService;

  public CartServiceTest() {
    cartService = new CartService(cartRepository);
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

  private void mockCartRepository(List<Cart> cartList) {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(cartList);
    BDDMockito.doNothing().when(cartRepository).delete(Mockito.any());
  }

  public static void assertCartNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
