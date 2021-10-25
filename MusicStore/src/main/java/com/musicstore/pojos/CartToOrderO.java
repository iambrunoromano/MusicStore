package com.musicstore.pojos;

import java.sql.Date;

public class CartToOrderO {

	public String id;
	public String mail;
	public Date date;
	public Double total;
	
	public CartToOrderO(String id, String mail, Date date, Double total) {
		super();
		this.id = id;
		this.mail = mail;
		this.date = date;
		this.total = total;
	}
}
