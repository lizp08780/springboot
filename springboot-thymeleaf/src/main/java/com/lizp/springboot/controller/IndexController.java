package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lizp.springboot.annotation.Log;
import com.lizp.springboot.persist.UserMapper;

@Controller
public class IndexController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@Transactional
	public String index(Model model) {
		userMapper.deleteByPrimaryKey(26);
		int x = 1 / 0;
		System.err.println(x);
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	@Log(desc = "测试记录日志")
	public String index2(Model model) {
		model.addAttribute("name", "Dear");
		return "index2";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

}
