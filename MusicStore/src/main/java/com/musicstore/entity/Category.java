package com.musicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = Category.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
  // TODO: category logic that was in store procedure put Integero category service
  // TODO: table names static + annotation for all entities
  // TODO: indexes annotation for all entities
  // TODO: active inactive status for all entities
  // TODO: dtInsert & dtUpdate for all entities
  public static final String TABLE_NAME = "category";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "parent")
  private Integer parent;

  @Column(name = "img_url")
  private String imgUrl;
}
