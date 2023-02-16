package com.musicstore.response;

import com.musicstore.entity.Cart;
import com.musicstore.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
  private Order order;
  private List<Cart> cartList;
}
