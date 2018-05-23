package com.lizp.springboot.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
	@Bean
	public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
		Map<String, CacheConfig> configMap = new ConcurrentHashMap<String, CacheConfig>();
		CacheConfig cacheConfig = new CacheConfig(100 * 1000, 50 * 1000);
		// cacheConfig.setMaxSize(maxSize);
		configMap.put("testCache", cacheConfig);

		CacheConfig cacheConfig2 = new CacheConfig(30 * 60 * 1000, 15 * 60 * 1000);
		// cacheConfig.setMaxSize(maxSize);
		configMap.put("testCache2", cacheConfig2);

		RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, configMap,
				redissonClient.getConfig().getCodec());
		return cacheManager;
	}

}
