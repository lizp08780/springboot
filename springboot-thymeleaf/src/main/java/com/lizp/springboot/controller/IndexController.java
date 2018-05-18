package com.lizp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", "Dear");
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	public String index2(Model model) {
		model.addAttribute("name", "Dear");
		return "index2";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public String error404() {
		return "errors/404";
	}

	@RequestMapping(value = "/error/500", method = RequestMethod.GET)
	public String error500() {
		return "errors/500";
	}

}
