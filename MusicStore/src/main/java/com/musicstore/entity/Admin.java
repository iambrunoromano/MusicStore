package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = Admin.TABLE_NAME)
@Table(name = Admin.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin extends AbstractEntity {
  public static final String TABLE_NAME = "admin";

  @Id
  @Column(name = "mail")
  private String mail;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "phone_number")
  private String phoneNumber;
}
