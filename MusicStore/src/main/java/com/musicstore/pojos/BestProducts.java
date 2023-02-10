package com.musicstore.pojos;

import lombok.Data;
import lombok.AllArgsConstructor;

// TODO: delete all pojos

public @Data @AllArgsConstructor class BestProducts {
	
	private Integer id;
	private String name;
	private Double price;
	private Integer quantity;
	private String producer;
	private Integer category;
	private String imgurl;
	
}
