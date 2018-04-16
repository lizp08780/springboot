package com.lizp.springboot.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap map) {
		map.put("title", "欢迎");
		return "index";
	}

}
