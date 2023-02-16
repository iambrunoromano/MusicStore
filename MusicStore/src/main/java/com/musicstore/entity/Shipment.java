package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = Shipment.TABLE_NAME)
@Table(name = Shipment.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment extends AbstractEntity {
  public static final String TABLE_NAME = "shipment";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "ship_date")
  private Timestamp shipDate;

  @Column(name = "arrive_date")
  private Timestamp arriveDate;

  @Column(name = "ship_address")
  private String shipAddress;

  @Column(name = "total")
  private double total;

  @Column(name = "order_id")
  private Integer orderId;
}
