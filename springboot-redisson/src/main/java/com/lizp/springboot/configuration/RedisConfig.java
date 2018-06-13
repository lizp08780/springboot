package com.lizp.springboot.configuration;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:spring-redis.xml" })
@EnableRedissonHttpSession // session共享
@EnableCaching // 开启缓存
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
