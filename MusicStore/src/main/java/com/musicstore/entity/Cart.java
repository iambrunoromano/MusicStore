package com.musicstore.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = Cart.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
  public static final String TABLE_NAME = "cart";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "mail")
  private String mail;

  @Column(name = "date")
  private Timestamp date;

  @Column(name = "bought")
  private boolean bought;

  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "overall_price")
  private Double overallPrice;
}
