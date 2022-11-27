package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartBean {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int product_id;
  private int quantity;
  private String mail;
  private Timestamp date;
}
