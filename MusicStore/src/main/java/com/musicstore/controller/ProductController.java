package com.musicstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
	
	@RequestMapping("/musicstore/addprod")
	public String home() {
		return "userhome";
	}
}
