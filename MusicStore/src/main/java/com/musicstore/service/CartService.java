package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.Product;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.ProductRepository;
import com.musicstore.request.CartRequest;
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
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  @Autowired
  public CartService(CartRepository cartRepository, ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
  }

  public Iterable<Cart> getAll() {
    return cartRepository.findAll();
  }

  public List<Cart> getByMail(String mail) {
    return cartRepository.findByMail(mail);
  }

  public List<Cart> getByOrderId(int orderId) {
    return cartRepository.findByOrderId(orderId);
  }

  public void save(List<CartRequest> cartRequestList) {
    List<Cart> cartList = new ArrayList<>();
    for (CartRequest cartRequest : cartRequestList) {
      cartList.add(createCart(cartRequest));
    }
    cartRepository.saveAll(cartList);
  }

  public boolean deleteByMail(String mail) {
    List<Cart> cartList = this.getByMail(mail);
    if (cartList.isEmpty()) {
      log.info("No cart rows found for user with mail [{}]", mail);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.CART_NOT_FOUND);
    }
    for (Cart cart : cartList) {
      cartRepository.delete(cart);
    }
    return true;
  }

  public Cart createCart(CartRequest cartRequest) {
    Optional<Product> optionalProduct = productRepository.findById(cartRequest.getProductId());
    if (optionalProduct.isPresent() && optionalProduct.get().getPrice() != null) {
      return Cart.builder()
          .productId(cartRequest.getProductId())
          .quantity(cartRequest.getQuantity())
          .mail(cartRequest.getMail())
          .date(cartRequest.getDate())
          .bought(false)
          .overallPrice(optionalProduct.get().getPrice() * cartRequest.getQuantity())
          .build();
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCT_NOT_FOUND);
  }
}
