package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = Cart.TABLE_NAME)
@Table(
    name = Cart.TABLE_NAME,
    indexes = {
      @Index(name = "cart_mail_index", columnList = "mail"),
      @Index(name = "cart_order_id_index", columnList = "order_id")
    })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart extends AbstractEntity {
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
