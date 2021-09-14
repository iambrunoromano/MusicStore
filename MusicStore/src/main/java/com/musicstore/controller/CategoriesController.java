package com.musicstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CategoriesController {

	@RequestMapping("/musicstore/categories")
	public String categories() {
		return "categories";
	}
	
	@RequestMapping("/musicstore/modcat")
	public String modcat() {
		return "modcat";
	}
}
