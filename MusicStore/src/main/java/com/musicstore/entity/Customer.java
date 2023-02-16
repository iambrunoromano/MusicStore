package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = Customer.TABLE_NAME)
@Table(name = Customer.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
  public static final String TABLE_NAME = "customer";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "payment_card")
  private String paymentCard;

  @Column(name = "billing_address")
  private String billingAddress;

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
