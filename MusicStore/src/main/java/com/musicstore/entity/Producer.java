package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = Producer.TABLE_NAME)
@Table(
    name = Producer.TABLE_NAME,
    indexes = {@Index(name = "producer_mail_index", columnList = "mail")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producer extends AbstractEntity {
  public static final String TABLE_NAME = "producer";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;
}
