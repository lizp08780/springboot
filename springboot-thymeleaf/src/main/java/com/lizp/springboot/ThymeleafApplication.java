package com.lizp.springboot;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//特别注意
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// @EnableTransactionManagement
@MapperScan("com.lizp.springboot.persist") // mapper 接口类扫描包配置
@ImportResource({ "classpath:spring-redis.xml" })
public class ThymeleafApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafApplication.class, args);
	}
}
