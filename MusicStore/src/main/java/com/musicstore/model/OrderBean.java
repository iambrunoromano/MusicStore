package com.musicstore.model;

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
public class OrderBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String mail;
	private Timestamp date;
	private double total;
	
	public OrderBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, mail, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		return Objects.equals(date, other.date) && id == other.id && Objects.equals(mail, other.mail)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", mail=" + mail + ", date=" + date + ", total=" + total + "]";
	}
		
}
