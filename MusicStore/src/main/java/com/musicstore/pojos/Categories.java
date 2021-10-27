package com.musicstore.pojos;

public class Categories {

	public Integer id;
	public String name;
	public Integer parent;
	public String imgurl;
	
	public Categories(Integer id, String name, Integer parent, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.imgurl = imgurl;
	}
}
