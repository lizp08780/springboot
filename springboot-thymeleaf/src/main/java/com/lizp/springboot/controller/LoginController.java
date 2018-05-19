package com.lizp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.util.Message;

@Controller
public class LoginController {
	@GetMapping({ "/login", "/" })
	public String login() {
		return "login";
	}

	@GetMapping("/unauth")
	public String unauth() {
		return "/error/unauth";
	}

	@PostMapping("/login")
	@ResponseBody
	public Message ajaxLogin(String username, String password, Boolean rememberMe) {
		// UsernamePasswordToken token = new UsernamePasswordToken(username, password,
		// rememberMe);
		// Subject subject = SecurityUtils.getSubject();
		// try {
		// subject.login(token);
		// return Message.success();
		// } catch (AuthenticationException e) {
		// String msg = "用户或密码错误";
		// if (StringUtils.isNotEmpty(e.getMessage())) {
		// msg = e.getMessage();
		// }
		// return Message.error(msg);
		// }
		return Message.error("用户或密码错误");
	}
}
