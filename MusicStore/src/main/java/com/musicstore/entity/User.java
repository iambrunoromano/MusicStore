package com.musicstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = User.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  public static final String TABLE_NAME = "user";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "password")
  private String password;

  @Column(name = "img_url")
  private String imgUrl;
}
