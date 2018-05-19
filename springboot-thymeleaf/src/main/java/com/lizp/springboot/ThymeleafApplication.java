package com.lizp.springboot;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@SpringBootApplication
// @EnableTransactionManagement
@MapperScan("com.lizp.springboot.persist") // mapper 接口类扫描包配置
public class ThymeleafApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafApplication.class, args);
	}

	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {
		// org.springframework.jdbc.datasource.DataSourceTransactionManager
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	@Bean
	public Object testBean2(SqlSessionTemplate sqlSessionTemplate) {
		System.out.println(">>>>>>>>>>" + sqlSessionTemplate.getConfiguration().getClass().getName());
		return new Object();
	}

	@Bean
	public Object testBean3(TransactionInterceptor transactionInterceptor) {
		// org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
		System.out.println(">>>>>>>>>>" + transactionInterceptor.getTransactionAttributeSource().getClass().getName());
		return new Object();
	}
}
