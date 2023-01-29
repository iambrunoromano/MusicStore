package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import com.musicstore.repository.CartRepository;
import com.musicstore.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

// TODO: logs
// TODO: exceptions for all cases of order not found etc.

@Service
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
    this.orderRepository = orderRepository;
    this.cartRepository = cartRepository;
  }

  public Iterable<Order> getAll() {
    return orderRepository.findAll();
  }

  public Order getVerifiedOrder(int orderId, String mail) {
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    if (!optionalOrder.isPresent()) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND);
    }
    Order order = optionalOrder.get();
    if (mail == null || !mail.equals(order.getMail())) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH);
    }
    return order;
  }

  public Order create(String mail) {
    List<Cart> cartList = cartRepository.findByMail(mail);
    Double total = 0.0;
    for (Cart cart : cartList) {
      total += cart.getOverallPrice();
    }
    return Order.builder().mail(mail).date(Timestamp.from(Instant.now())).total(total).build();
  }

  public Order save(Order order) {
    return orderRepository.save(order);
  }

  public boolean delete(int orderId) {
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    if (!optionalOrder.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.ORDER_NOT_FOUND);
    }
    orderRepository.delete(optionalOrder.get());
    return true;
  }
}
