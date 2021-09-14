package com.musicstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	@RequestMapping("/musicstore/loguser")
	public String loguser() {
		return "login";
	}
	
	@RequestMapping("/musicstore/login")
	public String login() {
		return "userhome";
	}
	
	@RequestMapping("/musicstore/logout")
	public String logout() {
		return "home";
	}
	
	@RequestMapping("/musicstore/userhome")
	public String userhome() {
		return "userhome";
	}
	
	@RequestMapping("/musicstore/admin")
	public String admin() {
		return "admin";
	}
}
