package com.lizp.springboot.configuration;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class RedisCacheManager extends AbstractTransactionSupportingCacheManager {
	private final RedissonClient redissonClient;
	private final CacheConfig defaultCacheConfig;
	private final Map<String, CacheConfig> initialCacheConfiguration;
	private final boolean allowInFlightCacheCreation;

	private RedisCacheManager(RedissonClient redissonClient, CacheConfig defaultCacheConfiguration,
			boolean allowInFlightCacheCreation) {
		Assert.notNull(redissonClient, "redissonClient must not be null!");
		Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");

		this.redissonClient = redissonClient;
		this.defaultCacheConfig = defaultCacheConfiguration;
		this.initialCacheConfiguration = new LinkedHashMap<>();
		this.allowInFlightCacheCreation = allowInFlightCacheCreation;
	}

	public RedisCacheManager(RedissonClient redissonClient, CacheConfig defaultCacheConfiguration,
			boolean allowInFlightCacheCreation, String... initialCacheNames) {
		this(redissonClient, defaultCacheConfiguration, allowInFlightCacheCreation);

		for (String cacheName : initialCacheNames) {
			this.initialCacheConfiguration.put(cacheName, defaultCacheConfiguration);
		}
	}

	public RedisCacheManager(RedissonClient redissonClient, CacheConfig defaultCacheConfiguration,
			boolean allowInFlightCacheCreation, Map<String, CacheConfig> initialCacheConfigurations) {
		this(redissonClient, defaultCacheConfiguration, allowInFlightCacheCreation);

		Assert.notNull(initialCacheConfigurations, "InitialCacheConfigurations must not be null!");

		this.initialCacheConfiguration.putAll(initialCacheConfigurations);
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		List<RedissonCache> caches = new LinkedList<>();

		for (Map.Entry<String, CacheConfig> entry : initialCacheConfiguration.entrySet()) {
			caches.add(createRedisCache(entry.getKey(), entry.getValue()));
		}
		return caches;
	}

	protected RedissonCache createRedisCache(String name, @Nullable CacheConfig cacheConfig) {
		return new RedissonCache(name, redissonClient, cacheConfig != null ? cacheConfig : defaultCacheConfig, true);
	}

	@Override
	protected RedissonCache getMissingCache(String name) {
		return allowInFlightCacheCreation ? createRedisCache(name, defaultCacheConfig) : null;
	}

}
