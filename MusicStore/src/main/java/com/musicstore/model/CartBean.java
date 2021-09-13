package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

public class CartBean {

	private int productId;
	private String mail;
	private Timestamp date;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	@Override
	public int hashCode() {
		return Objects.hash(date, mail, productId);
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
		return Objects.equals(date, other.date) && Objects.equals(mail, other.mail) && productId == other.productId;
	}
	@Override
	public String toString() {
		return "CartBean [productId=" + productId + ", mail=" + mail + ", date=" + date + "]";
	}
	
}
