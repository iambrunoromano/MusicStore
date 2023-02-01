package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "ship_date")
  private Timestamp shipDate;

  @Column(name = "arrive_date")
  private Timestamp arriveDate;

  @Column(name = "ship_address")
  private String shipAddress;

  @Column(name = "total")
  private double total;

  @Column(name = "order_id")
  private int orderId;
}
