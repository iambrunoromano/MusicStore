package com.musicstore.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;

import com.musicstore.pojos.Categories;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SqlResultSetMapping(
    name = "firstCategoryProcMapping",
    classes = {
      @ConstructorResult(
          targetClass = Categories.class,
          columns = {
            @ColumnResult(name = "id", type = Integer.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "parent", type = Integer.class),
            @ColumnResult(name = "imgurl", type = String.class)
          })
    })
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "categoryFirstProc",
      procedureName = "CategoriesByProducer",
      parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN,
            name = "producerMail",
            type = String.class)
      },
      resultSetMappings = {"firstCategoryProcMapping"})
})
public @Data @NoArgsConstructor class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private int parent;
  private String imgurl;
}
