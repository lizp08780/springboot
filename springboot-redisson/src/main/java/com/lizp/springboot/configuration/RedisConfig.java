package com.lizp.springboot.configuration;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

	/**
	 * 自带的redis缓存管理器不友好，以map存储；换成单对象存储
	 * 
	 * @param redissonClient
	 * @return
	 */
	@Bean
	public RedisCacheManager cacheManager(RedissonClient redissonClient) {
		CacheConfig cacheConfig = new CacheConfig(100 * 1000, 50 * 1000);// 全部用同一个默认的配置
		RedisCacheManager cacheManager = new RedisCacheManager(redissonClient, cacheConfig, true);
		return cacheManager;
	}

}
