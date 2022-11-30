package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
  // TODO: category logic that was in store procedure put into category service
  // TODO: table names static + annotation for all entities
  // TODO: indexes annotation for all entities
  // TODO: active inactive status for all entities
  // TODO: dtInsert & dtUpdate for all entities

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "parent")
  private int parent;

  @Column(name = "img_url")
  private String imgUrl;
}
