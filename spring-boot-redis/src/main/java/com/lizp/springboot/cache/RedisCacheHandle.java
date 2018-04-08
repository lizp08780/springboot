package com.lizp.springboot.cache;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.lizp.springboot.annotation.RedisCacheClean;
import com.lizp.springboot.annotation.RedisCacheGet;
import com.lizp.springboot.redis.RedisRepositry;
import com.lizp.springboot.serializer.JdkSerializer;

@Component
public class RedisCacheHandle implements RedisCache {
	// private static final int ONEDAY = 24 * 60 * 60;
	private final Logger log = LoggerFactory.getLogger(RedisCacheHandle.class);
	@Autowired
	private RedisRepositry redisRepositry;
	@Autowired
	private JdkSerializer serializer;

	@Override
	public Object redisCacheGet(Object[] args, Method method, ProceedingJoinPoint joinPoint) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("Enter: redisCacheGet() with argument[s] = {},method = {},joinPoint = {}", args,
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}

		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();

		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);

		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}

		RedisCacheGet methodType = method.getAnnotation(RedisCacheGet.class);
		String key = parser.parseExpression(methodType.key()).getValue(context, String.class);

		if (methodType.dataType() == RedisCacheGet.DataType.JSON) {// JSON形式保存
			if (redisRepositry.exists(key)) {
				long old = System.currentTimeMillis();
				String json = redisRepositry.get(key);
				log.debug("JSON read from redis spend > " + (System.currentTimeMillis() - old) + " <millis");
				if (method.getGenericReturnType().toString().contains("List")
						|| method.getGenericReturnType().toString().contains("Set")
						|| method.getGenericReturnType().toString().contains("Map")) {
					String clazzName = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0]
							.toString().substring(6);
					Object o = Class.forName(clazzName).newInstance();
					List list = null;// JsonSerializer.parseCollection(json, method.getReturnType(), o.getClass());
					return list;
				} else {
					// return JsonSerializer.parse(json, method.getReturnType());
				}
			} else {
				Object object = joinPoint.proceed(args);
				setRedisValueJson(methodType, key, object);
				return object;
			}
		} else {
			if (redisRepositry.exists(key)) {
				long old = System.currentTimeMillis();
				Object o = redisRepositry.get(key);
				log.debug("CLASS read from redis spend > " + (System.currentTimeMillis() - old) + " <millis");
				return o;
			} else {// 查询数据，缓存，返回对象
				long l = System.currentTimeMillis();
				Object object = joinPoint.proceed(args);
				log.debug("read from DB spend > " + (System.currentTimeMillis() - l) + " <millis");
				setRedisValueClass(methodType, key, object);
				return object;
			}

		}
		return null;
	}

	@Override
	public Object redisCacheClean(Object[] args, Method method, ProceedingJoinPoint joinPoint) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("Enter: redisCacheClean() with argument[s] = {},method = {},joinPoint = {}", args,
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();

		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);

		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		Object object = joinPoint.proceed(args);

		RedisCacheClean methodType = method.getAnnotation(RedisCacheClean.class);
		for (String str : methodType.key()) {
			String key = parser.parseExpression(str).getValue(context, String.class);
			if (str.contains("*")) {
				Set<String> keys = redisRepositry.hkeys(key);
				for (String key1 : keys) {
					redisRepositry.del(key1);
				}
			}
			redisRepositry.del(key);
		}
		return object;
	}

	private void setRedisValueClass(RedisCacheGet methodType, String key, Object object) {
		StopWatch watch = new StopWatch("setRedisValueClass");
		watch.start();
		if (object != null) {
			if (methodType.expire() == 0) {
				redisRepositry.set(serializer.serialize(key), serializer.serialize(object));
			} else {
				redisRepositry.set(serializer.serialize(key), serializer.serialize(object), methodType.expire());
			}
		}
		watch.stop();
		log.debug("{}", watch.prettyPrint());
	}

	private void setRedisValueJson(RedisCacheGet methodType, String key, Object value) {
		StopWatch watch = new StopWatch("setRedisValueJson");
		watch.start();
		if (value != null) {
			if (methodType.expire() == 0) {
				redisRepositry.set(key, value.toString());
			} else {
				redisRepositry.set(serializer.serialize(key), serializer.serialize(value), methodType.expire());
			}

		}
		watch.stop();
		log.debug("{}", watch.prettyPrint());
	}
}
