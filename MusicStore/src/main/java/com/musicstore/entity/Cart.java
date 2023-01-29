package com.musicstore.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: add in cart logic overall price computation before insertion

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "product_id")
  private int productId;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "mail")
  private String mail;

  @Column(name = "date")
  private Timestamp date;

  @Column(name = "bought")
  private boolean bought;

  @Column(name = "order_id")
  private int orderId;

  @Column(name = "overall_price")
  private double overallPrice;
}
