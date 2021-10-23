package com.musicstore.pojos;

public class BestProducts {
	
	public Integer id;
	public String name;
	public Double price;
	public Integer quantity;
	public String producer;
	public Integer category;
	public String imgurl;
	public BestProducts(Integer id, String name, Double price, Integer quantity, String producer,
			Integer category, String imgurl) {
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
