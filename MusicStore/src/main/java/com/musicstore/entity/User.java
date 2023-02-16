package com.musicstore.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = User.TABLE_NAME)
@Table(
    name = User.TABLE_NAME,
    indexes = {@Index(name = "product_mail_password_index", columnList = "mail,password")})
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
