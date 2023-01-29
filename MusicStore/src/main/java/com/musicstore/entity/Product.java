package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  // TODO:indexes on all entities on column repositories search on

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private Double price;

  @Column(name = "left_quantity")
  private int leftQuantity;

  @Column(name = "sold_quantity")
  private int soldQuantity;

  @Column(name = "producer")
  private String producer;

  @Column(name = "category")
  private int category;

  @Column(name = "img_url")
  private String imgUrl;
}
