package com.lizp.springboot.i18n;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//MessageSourceAutoConfiguration
@Configuration
public class I18nConfig {
	private static Logger logger = LoggerFactory.getLogger(I18nConfig.class);

	@Bean(name = "localeResolver") // 对象的名称必须为localeResolver，Spring容器会自动加载，否则找不到。
	public MyLocaleResolver myLocaleResolver() {
		logger.info("#####cookieLocaleResolver---create");
		MyLocaleResolver myLocaleResolver = new MyLocaleResolver();
		myLocaleResolver.setDefaultLocale(Locale.US);
		logger.info("#####cookieLocaleResolver:");
		return myLocaleResolver;
	}
}
