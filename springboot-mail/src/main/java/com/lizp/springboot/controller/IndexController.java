package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.service.MailService;

@RestController
public class IndexController {
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Long id) {
		mailService.sendSimpleMail("456@qq.com", "主题", "你好");
		return "ok";
	}

}
