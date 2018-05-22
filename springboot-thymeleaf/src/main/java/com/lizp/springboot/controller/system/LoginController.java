package com.lizp.springboot.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.util.HttpUtils;
import com.lizp.springboot.util.Message;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping({ "/login", "/" })
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return "redirect:/index";
		}
		return "login";
	}

	@GetMapping("/unauth")
	public String unauth() {
		return "/error/unauth";
	}

	@PostMapping("/login")
	@ResponseBody
	public Message ajaxLogin(String validateCode, String username, String password, Boolean rememberMe,
			HttpServletRequest request) {
		logger.info("----------");
		Subject subject = SecurityUtils.getSubject();
		// 判断验证码
		String host = HttpUtils.getClientIp(request);
		// RSAPrivateKeyInfo privateKey = rsaService.getPrivateKey();
		// java.security.interfaces.RSAPrivateKey pk =
		// RSAUtil.generateRSAPrivateKeyFromHex(privateKey.getModulus(),
		// privateKey.getPrivateExponent());
		// String plainPassword = PasswordUtils.decryptPasswordWithRSA(pk,
		// login.getPassword());
		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);

		// System.err.println("2:" + password);
		// Object obj =
		// SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// String code = String.valueOf(obj != null ? obj : "");
		// if (StringUtils.isBlank(validateCode) ||
		// !validateCode.equalsIgnoreCase(code)) {
		// return Message.error("验证码错误");
		// }
		try {
			subject.login(token);
			return Message.success();
		} catch (AuthenticationException e) {
			String msg = "用户或密码错误";
			return Message.error(msg);
		}
	}
}
