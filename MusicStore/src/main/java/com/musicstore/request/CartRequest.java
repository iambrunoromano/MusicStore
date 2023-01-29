package com.musicstore.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CartRequest {
  private int productId;
  private int quantity;
  private String mail;
  private Timestamp date;
}
