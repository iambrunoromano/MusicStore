package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = User.TABLE_NAME)
@Table(
    name = User.TABLE_NAME,
    indexes = {@Index(name = "product_mail_password_index", columnList = "mail,password")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends AbstractEntity {
  public static final String TABLE_NAME = "user";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "password")
  private String password;

  @Column(name = "img_url")
  private String imgUrl;
}
