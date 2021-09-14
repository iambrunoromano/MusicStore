package com.musicstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
	
	@RequestMapping("/musicstore/cart")
	public String home() {
		return "cart";
	}
}
