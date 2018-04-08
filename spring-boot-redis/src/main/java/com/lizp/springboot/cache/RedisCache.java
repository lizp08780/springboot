package com.lizp.springboot.cache;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

public interface RedisCache {
	Object redisCacheGet(Object[] args, Method method, ProceedingJoinPoint joinPoint) throws Throwable;

	Object redisCacheClean(Object[] args, Method method, ProceedingJoinPoint joinPoint) throws Throwable;
}
