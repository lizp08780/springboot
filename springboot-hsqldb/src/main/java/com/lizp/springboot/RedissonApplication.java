package com.lizp.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:spring-redis.xml" })
public class RedissonApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedissonApplication.class, args);
	}
}
