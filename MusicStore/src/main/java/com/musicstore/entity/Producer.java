package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = Producer.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producer {
  public static final String TABLE_NAME = "producer";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;
}
