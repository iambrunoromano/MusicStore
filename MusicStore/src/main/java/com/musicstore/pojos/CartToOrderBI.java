package com.musicstore.pojos;

import lombok.Data;
import lombok.AllArgsConstructor;

public @Data @AllArgsConstructor class CartToOrderBI{

	private String mail;
	private Integer productId;
	private Integer orderId;
	private Integer quantity;
	private Double price;
	
}