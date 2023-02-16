package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = Product.TABLE_NAME)
@Table(
    name = Product.TABLE_NAME,
    indexes = {
      @Index(name = "product_producer_index", columnList = "producer"),
      @Index(name = "product_category_index", columnList = "category"),
      @Index(name = "product_sold_quantity_index", columnList = "sold_quantity")
    })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
  public static final String TABLE_NAME = "product";

  // TODO:indexes on all entities on column repositories search on

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private Double price;

  @Column(name = "left_quantity")
  private Integer leftQuantity;

  @Column(name = "sold_quantity")
  private Integer soldQuantity;

  @Column(name = "producer")
  private String producer;

  @Column(name = "category")
  private Integer category;

  @Column(name = "img_url")
  private String imgUrl;

  @CreationTimestamp
  @Column(
      name = "insert_date",
      updatable = false,
      insertable = false,
      columnDefinition = " DATETIME DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime insertDate;

  @UpdateTimestamp
  @Column(
      name = "update_date",
      insertable = false,
      columnDefinition = " DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime updateDate;
}
