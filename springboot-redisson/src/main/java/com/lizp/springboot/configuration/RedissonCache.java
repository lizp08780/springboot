package com.lizp.springboot.configuration;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.NullValue;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class RedissonCache implements Cache {
	private final String name;
	private RedissonClient redissonClient;
	private CacheConfig config;
	private final boolean allowNullValues;

	public RedissonCache(String name, RedissonClient redissonClient, CacheConfig config, boolean allowNullValues) {
		this.name = name;
		this.redissonClient = redissonClient;
		this.config = config;
		this.allowNullValues = allowNullValues;
	}

	public RedissonCache(String name, RedissonClient redissonClient, boolean allowNullValues) {
		this.name = name;
		this.redissonClient = redissonClient;
		this.allowNullValues = allowNullValues;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this.redissonClient;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = redissonClient.getBucket(this.name + ":" + key).get();
		return toValueWrapper(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = redissonClient.getBucket(this.name + ":" + key).get();
		if (value == null) {

		} else {
			if (value.getClass().getName().equals(NullValue.class.getName())) {
				return null;
			}
			if (type != null && !type.isInstance(value)) {
				throw new IllegalStateException(
						"Cached value is not of required type [" + type.getName() + "]: " + value);
			}
		}
		return (T) fromStoreValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		Object value = redissonClient.getBucket(this.name + ":" + key).get();
		if (value == null) {
			RLock lock = redissonClient.getLock(this.name + ":" + key);
			lock.lock();
			try {
				value = redissonClient.getBucket(this.name + ":" + key).get();
				if (value == null) {
					try {
						value = toStoreValue(valueLoader.call());
					} catch (Throwable ex) {
						RuntimeException exception;
						try {
							Class<?> c = Class.forName("org.springframework.cache.Cache$ValueRetrievalException");
							Constructor<?> constructor = c.getConstructor(Object.class, Callable.class,
									Throwable.class);
							exception = (RuntimeException) constructor.newInstance(this.name + ":" + key, valueLoader,
									ex);
						} catch (Exception e) {
							throw new IllegalStateException(e);
						}
						throw exception;
					}
					put(this.name + ":" + key, value);
				}
			} finally {
				lock.unlock();
			}
		} else {
		}
		return (T) fromStoreValue(value);
	}

	@Override
	public void put(Object key, Object value) {
		if (!allowNullValues && value == null) {
			redissonClient.getBucket(this.name + ":" + key).delete();
			return;
		}
		value = toStoreValue(value);
		redissonClient.getBucket(this.name + ":" + key).set(value, config.getTTL(), TimeUnit.MILLISECONDS);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object prevValue;
		if (!allowNullValues && value == null) {
			prevValue = redissonClient.getBucket(this.name + ":" + key).get();
		} else {
			value = toStoreValue(value);
			redissonClient.getBucket(this.name + ":" + key).set(value, config.getTTL(), TimeUnit.MILLISECONDS);
			prevValue = value;
		}
		return toValueWrapper(prevValue);
	}

	@Override
	public void evict(Object key) {
		redissonClient.getBucket(this.name + ":" + key).delete();
	}

	@Override
	public void clear() {
		redissonClient.getKeys().deleteByPattern(this.name + ":*");// 模糊删除
	}

	protected Object fromStoreValue(Object storeValue) {
		if (storeValue instanceof NullValue) {
			return null;
		}
		return storeValue;
	}

	protected Object toStoreValue(Object userValue) {
		if (userValue == null) {
			return NullValue.INSTANCE;
		}
		return userValue;
	}

	private ValueWrapper toValueWrapper(Object value) {
		if (value == null) {
			return null;
		}
		if (value.getClass().getName().equals(NullValue.class.getName())) {
			return NullValue.INSTANCE;
		}
		return new SimpleValueWrapper(value);
	}

}
