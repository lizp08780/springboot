package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lizp.springboot.bean.TestBean;

@Controller
public class IndexController {

	@Autowired
	private TestBean testBean;

	@RequestMapping(value = "/index")
	public String index(ModelMap map) {
		testBean.test("haha");
		map.put("title", "欢迎");
		System.err.println(System.getProperties());
		return "index";
	}

}
