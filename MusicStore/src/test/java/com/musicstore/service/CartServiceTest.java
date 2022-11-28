package com.musicstore.service;

import com.musicstore.entity.Cart;
import com.musicstore.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
  private CartRepository cartRepository = Mockito.mock(CartRepository.class);
  private CartService cartService;

  public CartServiceTest() {
    cartService = new CartService(cartRepository);
  }

  @Test
  void deleteNotFound() {
    mockCartRepository(new ArrayList<>());
    assertFalse(cartService.deleteByMail("not_found_mail"));
  }

  @Test
  void deleteFound() {
    List<Cart> cartList = new ArrayList<>();
    cartList.add(buildCart(1, 1, 1, "mail_1", Timestamp.from(Instant.now())));
    cartList.add(buildCart(2, 2, 2, "mail_2", Timestamp.from(Instant.now())));
    mockCartRepository(cartList);
    assertTrue(cartService.deleteByMail("found_mail"));
  }

  private void mockCartRepository(List<Cart> cartList) {
    BDDMockito.given(cartRepository.findByMail(Mockito.anyString())).willReturn(cartList);
    BDDMockito.doNothing().when(cartRepository).delete(Mockito.any());
  }

  private Cart buildCart(
      Integer id, Integer productId, Integer quantity, String mail, Timestamp date) {
    return Cart.builder()
        .id(id)
        .productId(productId)
        .quantity(quantity)
        .mail(mail)
        .date(date)
        .build();
  }
}
