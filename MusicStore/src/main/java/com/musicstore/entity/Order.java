package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = Order.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  public static final String TABLE_NAME = "order";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "mail")
  private String mail;

  @Column(name = "date")
  private Timestamp date;

  @Column(name = "total")
  private double total;

  @Column(name = "address")
  private String address;
}
