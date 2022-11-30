package com.musicstore.model;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import javax.persistence.ParameterMode;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.musicstore.pojos.BestProducts;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SqlResultSetMapping(
    name = "firstProductProcMapping",
    classes = {
      @ConstructorResult(
          targetClass = BestProducts.class,
          columns = {
            @ColumnResult(name = "id", type = Integer.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "price", type = Double.class),
            @ColumnResult(name = "quantity", type = Integer.class),
            @ColumnResult(name = "producer", type = String.class),
            @ColumnResult(name = "category", type = Integer.class),
            @ColumnResult(name = "imgurl", type = String.class)
          })
    })
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "productFirstProc",
      procedureName = "BestProducts",
      resultSetMappings = {"firstProductProcMapping"}),
  @NamedStoredProcedureQuery(
      name = "productSecondProc",
      procedureName = "ProductsByProducer",
      parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN,
            name = "producerMail",
            type = String.class)
      },
      resultSetMappings = {"firstProductProcMapping"}),
  @NamedStoredProcedureQuery(
      name = "productThirdProc",
      procedureName = "ProductsByCart",
      parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_mail", type = String.class)
      },
      resultSetMappings = {"firstProductProcMapping"}),
  @NamedStoredProcedureQuery(
      name = "productFourthProc",
      procedureName = "ProductsByCategory",
      parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN,
            name = "categoryId",
            type = Integer.class)
      },
      resultSetMappings = {"firstProductProcMapping"})
})
public @Data @NoArgsConstructor class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private double price;
  private int quantity;
  private String producer;
  private int category;
  private String imgurl;
}
