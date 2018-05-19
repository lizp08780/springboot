package com.lizp.springboot.controller.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class I18nControl {
	private static Logger logger = LoggerFactory.getLogger(I18nControl.class);

	@GetMapping("/checklanguage/{language}")
	public String language(HttpServletRequest request, HttpServletResponse response, @PathVariable String language) {
		// 打印日志
		Locale locale = request.getLocale();
		logger.error("{}", locale);
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		logger.info("language:{}", language);
		if (language == null || "".equals(language)) {
			return "index";
		} else {
			if ("CN".equals(language)) {
				localeResolver.setLocale(request, response, Locale.CHINA);// 会话区域解析器
			} else if ("US".equals(language)) {
				localeResolver.setLocale(request, response, Locale.US);
			} else {
				localeResolver.setLocale(request, response, Locale.CHINA);
			}
		}
		return "index";
	}
}
