package com.musicstore.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public abstract class AbstractEntity {

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
