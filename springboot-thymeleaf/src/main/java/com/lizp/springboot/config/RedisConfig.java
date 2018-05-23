package com.lizp.springboot.config;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.lizp.springboot.lock.RedissonLockTemplate;

@ImportResource({ "classpath:spring-redis.xml" })
@Configuration
public class RedisConfig {

	@Bean
	public RedissonLockTemplate lockTemplate(RedissonClient redissonClient) {
		return new RedissonLockTemplate(redissonClient);
	}
}
