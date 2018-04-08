package com.lizp.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "spring.datasource.main")
@Component
@Validated
public class DataSourceProperties {
	private String username;
	// @NotBlank(message = "测试字段不能为空")
	private String test;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		System.err.println("加载配置：" + username);
		this.username = username;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Bean
	public TestBean getTestBean() {
		return new TestBean();
	}
}
