package com.lizp.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

@Component
public class AbstractRedisRepositry {
	@Autowired
	private JedisPool jedisPool;

	public <E> E doOperation(JedisCallBack<E> callback) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return callback.handle(jedis);
		} catch (JedisConnectionException e) {
			throw new JedisException("Faild when execute operation  " + e);
		} finally {
			if (null != jedisPool && null != jedis) {
				jedis.close();
			}
		}
	}
}
