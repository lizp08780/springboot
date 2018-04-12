package com.lizp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value = "/index")
	public String index(ModelMap map) {
		map.put("title", "欢迎");
		System.err.println(System.getProperties());
		return "index";
	}

}
