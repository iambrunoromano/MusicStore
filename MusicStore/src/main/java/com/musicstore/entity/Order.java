package com.musicstore.entity;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;

import com.musicstore.pojos.CartToOrderBI;
import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SqlResultSetMapping(name="firstOrderProdMapping", classes= {
		@ConstructorResult(targetClass = CartToOrderBI.class,
				   columns = {
						   @ColumnResult(name="mail", type = String.class),
						   @ColumnResult(name="productId", type = Integer.class),
						   @ColumnResult(name="orderId", type = Integer.class),
						   @ColumnResult(name="quantity", type = Integer.class),
						   @ColumnResult(name="price", type = Double.class)
				   })
})
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name="orderFirstProc", 
			procedureName="CartToOrder", 
			parameters= {@StoredProcedureParameter(mode=ParameterMode.IN, name="user_mail", type=String.class)}, 
			resultSetMappings = {"firstOrderProdMapping"})})
public @Data @NoArgsConstructor class OrderBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String mail;
	private Timestamp date;
	private double total;
	
}
