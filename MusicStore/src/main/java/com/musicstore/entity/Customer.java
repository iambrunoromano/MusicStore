package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = Customer.TABLE_NAME)
@Table(name = Customer.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends AbstractEntity {
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
}
