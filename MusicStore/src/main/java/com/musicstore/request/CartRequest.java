package com.musicstore.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CartRequest {
  private int productId;
  private int quantity;
  private String mail;
  private Timestamp date;
}
