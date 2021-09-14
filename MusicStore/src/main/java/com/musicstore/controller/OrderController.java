package com.musicstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
	
	@RequestMapping("/musicstore/orders")
	public String orders() {
		return "orders";
	}
}
