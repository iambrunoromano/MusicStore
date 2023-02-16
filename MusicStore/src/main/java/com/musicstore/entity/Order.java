package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = Order.TABLE_NAME)
@Table(name = Order.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  public static final String TABLE_NAME = "order_table";

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
