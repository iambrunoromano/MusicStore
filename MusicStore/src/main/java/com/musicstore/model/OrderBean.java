package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

public class OrderBean {

	private int id;
	private Timestamp date;
	private double total;
	private String mail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
		return "OrderBean [id=" + id + ", date=" + date + ", total=" + total + ", mail=" + mail + "]";
	}
	
}
