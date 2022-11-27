package com.musicstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "password")
  private String password;

  @Column(name = "img_url")
  private String imgUrl;
}
