package com.musicstore.pojos;

import java.math.BigInteger;
import java.math.BigDecimal;

public class BestProducts {
	
	public BigInteger id;
	public String name;
	public BigDecimal price;
	public BigInteger quantity;
	public String producer;
	public BigInteger category;
	public String imgurl;
	public BestProducts(BigInteger id, String name, BigDecimal price, BigInteger quantity, String producer,
			BigInteger category, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.producer = producer;
		this.category = category;
		this.imgurl = imgurl;
	}
	
}
