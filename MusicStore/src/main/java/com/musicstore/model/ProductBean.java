package com.musicstore.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.musicstore.pojos.BestProducts;

@Entity
@SqlResultSetMapping(name="firstProductProcMapping", classes= {
		@ConstructorResult(targetClass = BestProducts.class,
						   columns = {
								   @ColumnResult(name="id", type = Integer.class),
								   @ColumnResult(name="name", type = String.class),
								   @ColumnResult(name="price", type = Double.class),
								   @ColumnResult(name="quantity", type = Integer.class),
								   @ColumnResult(name="producer", type = String.class),
								   @ColumnResult(name="category", type = Integer.class),
								   @ColumnResult(name="imgurl", type = String.class)
						   })
})
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name="productFirstProc", 
			procedureName="BestProducts", 
			resultSetMappings = {"firstProductProcMapping"}),
	@NamedStoredProcedureQuery(
			name="productSecondProc", 
			procedureName="ProductsByProducer", 
			parameters= {@StoredProcedureParameter(mode=ParameterMode.IN, name="producerMail", type=String.class)}, 
			resultSetMappings = {"firstProductProcMapping"}),
	@NamedStoredProcedureQuery(
			name="productThirdProc", 
			procedureName="ProductsByCart", 
			parameters= {@StoredProcedureParameter(mode=ParameterMode.IN, name="user_mail", type=String.class)}, 
			resultSetMappings = {"firstProductProcMapping"}),
	@NamedStoredProcedureQuery(
			name="productFourthProc", 
			procedureName="ProductsByCategory", 
			parameters= {@StoredProcedureParameter(mode=ParameterMode.IN, name="categoryId", type=Integer.class)}, 
			resultSetMappings = {"firstProductProcMapping"})
	})
public class ProductBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int  id;
	
	private String name;
	private double price;
	private int quantity;
	private String producer;
	private int category;
	private String imgurl;

	public ProductBean() {}
	
	public ProductBean(int id, String name, double price, int quantity, String producer, int category, String imgurl) {
		this.id= id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.producer = producer;
		this.category = category;
		this.imgurl = imgurl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void update(ProductBean pb) {
		this.name = pb.getName();
		this.price = pb.getPrice();
		this.quantity = pb.getQuantity();
		this.producer = pb.getProducer();
		this.category = pb.getCategory();
		this.imgurl = pb.getImgurl();
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, id, imgurl, name, price, producer, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductBean other = (ProductBean) obj;
		return category == other.category && id == other.id && Objects.equals(imgurl, other.imgurl)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(producer, other.producer) && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "ProductBean [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity
				+ ", producer=" + producer + ", category=" + category + ", imgurl=" + imgurl + "]";
	} 

}
