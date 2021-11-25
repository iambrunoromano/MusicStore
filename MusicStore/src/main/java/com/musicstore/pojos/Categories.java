package com.musicstore.pojos;

import lombok.Data;
import lombok.AllArgsConstructor;

public @Data @AllArgsConstructor class Categories {

	private Integer id;
	private String name;
	private Integer parent;
	private String imgurl;
	
}
