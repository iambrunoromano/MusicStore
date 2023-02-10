package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = Product.TABLE_NAME)
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
}
