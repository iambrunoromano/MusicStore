package com.musicstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/musicstore/home")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/musicstore/demo")
	@ResponseBody
	public String demo() {
		return "Demo Page";
	}
}
