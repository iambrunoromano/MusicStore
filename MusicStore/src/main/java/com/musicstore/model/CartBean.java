package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CartBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int product_id;
	private int quantity;
	private String mail;
	private Timestamp date;
	
	public CartBean() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, mail, product_id, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartBean other = (CartBean) obj;
		return Objects.equals(date, other.date) && id == other.id && Objects.equals(mail, other.mail)
				&& product_id == other.product_id && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "CartBean [id=" + id + ", product_id=" + product_id + ", quantity=" + quantity + ", mail=" + mail
				+ ", date=" + date + "]";
	}
	
}
