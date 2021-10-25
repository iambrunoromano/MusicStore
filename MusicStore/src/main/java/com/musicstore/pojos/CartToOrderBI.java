package com.musicstore.pojos;

public class CartToOrderBI {

	public String mail;
	public Integer productId;
	public Integer orderId;
	public Integer quantity;
	public Double price;
	
	public CartToOrderBI(String mail, Integer productId, Integer orderId, Integer quantity, Double price) {
		super();
		this.mail = mail;
		this.productId = productId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.price = price;
	}
}