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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private double price;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "producer")
  private String producer;

  @Column(name = "category")
  private int category;

  @Column(name = "img_url")
  private String imgUrl;
}
