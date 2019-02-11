package com.sample2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopPageController {

	@GetMapping("/top")
	public String firstAccess() {
		return "/top";
	}
	
	@GetMapping("tosearch")
	public String toSearch() {
		return "/userpage/search";
	}
}
