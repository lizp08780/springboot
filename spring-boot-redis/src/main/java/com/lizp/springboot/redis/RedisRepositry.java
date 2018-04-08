package com.lizp.springboot.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.util.SafeEncoder;

@Component
public class RedisRepositry extends AbstractRedisRepositry implements RedisOperation {

	@Override
	public boolean exists(String key) {
		return doOperation(jedis -> jedis.exists(key));
	}

	@Override
	public boolean exists(byte[] key) {
		return doOperation(jedis -> jedis.exists(key));
	}

	@Override
	public String setBatch(int DBIndex, Map<String, String> values) {
		return doOperation(jedis -> {
			jedis.select(DBIndex);
			Pipeline p = jedis.pipelined();
			Set<String> keys = values.keySet();
			for (String key : keys) {
				p.set(key, values.get(key));
			}
			p.sync();
			return values.toString();
		});
	}

	@Override
	public Map<String, String> getBatch(int DBIndex, List<String> keys) {
		return doOperation(jedis -> {
			Map<String, String> result = new HashMap<>();
			Map<String, Response<String>> resultR = new HashMap<>();
			jedis.select(DBIndex);
			Pipeline p = jedis.pipelined();
			for (String key1 : keys) {
				resultR.put(key1, p.get(key1));
			}
			p.sync();
			Set<String> sets = resultR.keySet();
			for (String key : sets) {
				result.put(key, resultR.get(key).get());
			}
			return result;
		});
	}

	@Override
	public String hsetBatch(int DBIndex, String key, Map<String, String> values) {
		return doOperation(jedis -> {
			jedis.select(DBIndex);
			Pipeline p = jedis.pipelined();
			Set<String> keys = values.keySet();
			for (String fiedlKey : keys) {
				p.hset(key, fiedlKey, values.get(key));
			}
			p.sync();
			return values.toString();
		});
	}

	@Override
	public String set(String key, String value) {
		return doOperation(jedis -> jedis.set(key, value));
	}

	@Override
	public String set(String key, String value, int dbIndex) {
		return doOperation(jedis -> {
			jedis.select(dbIndex);
			return jedis.set(key, value);
		});
	}

	@Override
	public String set(String key, String value, int dbIndex, int seconds) {
		return doOperation(jedis -> {
			jedis.select(dbIndex);
			return jedis.setex(key, seconds, value);
		});
	}

	@Override
	public String set(byte[] key, byte[] value) {
		return doOperation(jedis -> jedis.set(key, value));
	}

	@Override
	public String set(byte[] key, byte[] value, int dbIndex) {
		return doOperation(jedis -> {
			jedis.select(dbIndex);
			return jedis.set(key, value);
		});
	}

	@Override
	public String set(byte[] key, byte[] value, int dbIndex, int seconds) {
		return doOperation(jedis -> {
			jedis.select(dbIndex);
			return jedis.setex(key, seconds, value);
		});
	}

	@Override
	public String get(String key) {
		return doOperation(jedis -> jedis.get(key));
	}

	@Override
	public String type(String key) {
		return doOperation(jedis -> jedis.type(key));
	}

	@Override
	public Long expire(String key, int seconds) {
		return doOperation(jedis -> jedis.expire(key, seconds));
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		return doOperation(jedis -> jedis.expireAt(key, unixTime));
	}

	@Override
	public Long ttl(String key) {
		return doOperation(jedis -> jedis.ttl(key));
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		return doOperation(jedis -> jedis.setbit(key, offset, value));
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		return doOperation(jedis -> jedis.setbit(SafeEncoder.encode(key), offset, SafeEncoder.encode(value)));
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return doOperation(jedis -> jedis.getbit(key, offset));
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return doOperation(jedis -> jedis.setrange(key, offset, value));
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return doOperation(jedis -> jedis.getrange(key, startOffset, endOffset));
	}

	@Override
	public String getSet(String key, String value) {
		return doOperation(jedis -> jedis.getSet(key, value));
	}

	@Override
	public Long setnx(String key, String value) {
		return doOperation(jedis -> jedis.setnx(key, value));
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return doOperation(jedis -> jedis.setex(key, seconds, value));
	}

	@Override
	public Long decrBy(String key, long integer) {
		return doOperation(jedis -> jedis.decrBy(key, integer));
	}

	@Override
	public Long decr(String key) {
		return doOperation(jedis -> jedis.decr(key));
	}

	@Override
	public Long incrBy(String key, long integer) {
		return doOperation(jedis -> jedis.incrBy(key, integer));
	}

	@Override
	public Long incr(String key) {
		return doOperation(jedis -> jedis.incr(key));
	}

	@Override
	public Long append(String key, String value) {
		return doOperation(jedis -> jedis.append(key, value));
	}

	@Override
	public String substr(String key, int start, int end) {
		return doOperation(jedis -> jedis.substr(key, start, end));
	}

	@Override
	public Long hset(String key, String field, String value) {
		return doOperation(jedis -> jedis.hset(key, field, value));
	}

	@Override
	public String hget(String key, String field) {
		return doOperation(jedis -> jedis.hget(key, field));
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return doOperation(jedis -> jedis.hsetnx(key, field, value));
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return doOperation(jedis -> jedis.hmset(key, hash));
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return doOperation(jedis -> jedis.hmget(key, fields));
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return doOperation(jedis -> jedis.hincrBy(key, field, value));
	}

	@Override
	public Boolean hexists(String key, String field) {
		return doOperation(jedis -> jedis.hexists(key, field));
	}

	@Override
	public Long del(String key) {
		return doOperation(jedis -> jedis.del(key));
	}

	@Override
	public Long hdel(String key, String... fields) {
		return doOperation(jedis -> jedis.hdel(key, fields));
	}

	@Override
	public Long hlen(String key) {
		return doOperation(jedis -> jedis.hlen(key));
	}

	@Override
	public Set<String> hkeys(String key) {
		return doOperation(jedis -> jedis.hkeys(key));
	}

	@Override
	public List<String> hvals(String key) {
		return doOperation(jedis -> jedis.hvals(key));
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return doOperation(jedis -> jedis.hgetAll(key));
	}

	@Override
	public Long rpush(String key, String... strings) {
		return doOperation(jedis -> jedis.rpush(key, strings));
	}

	@Override
	public Long lpush(String key, String... strings) {
		return doOperation(jedis -> jedis.lpush(key, strings));
	}

	@Override
	public Long lpushx(String key, String string) {
		return doOperation(jedis -> jedis.lpushx(key, string));
	}

	@Override
	public Long strlen(String key) {
		return doOperation(jedis -> jedis.strlen(key));
	}

	@Override
	public Long move(String key, int dbIndex) {
		return doOperation(jedis -> jedis.move(key, dbIndex));
	}

	@Override
	public Long rpushx(String key, String string) {
		return doOperation(jedis -> jedis.rpushx(key, string));
	}

	@Override
	public Long persist(String key) {
		return doOperation(jedis -> jedis.persist(key));
	}

	@Override
	public Long llen(String key) {
		return doOperation(jedis -> jedis.llen(key));
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return doOperation(jedis -> jedis.lrange(key, start, end));
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return doOperation(jedis -> jedis.ltrim(key, start, end));
	}

	@Override
	public String lindex(String key, long index) {
		return doOperation(jedis -> jedis.lindex(key, index));
	}

	@Override
	public String lset(String key, long index, String value) {
		return doOperation(jedis -> jedis.lset(key, index, value));
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return doOperation(jedis -> jedis.lrem(key, count, value));
	}

	@Override
	public String lpop(String key) {
		return doOperation(jedis -> jedis.lpop(key));
	}

	@Override
	public String rpop(String key) {
		return doOperation(jedis -> jedis.rpop(key));
	}

	@Override
	public Long sadd(String key, String... members) {
		return doOperation(jedis -> jedis.sadd(key, members));
	}

	@Override
	public Set<String> smembers(String key) {
		return doOperation(jedis -> jedis.smembers(key));
	}

	@Override
	public Long srem(String key, String... members) {
		return doOperation(jedis -> jedis.srem(key, members));
	}

	@Override
	public String spop(String key) {
		return doOperation(jedis -> jedis.spop(key));
	}

	@Override
	public Long scard(String key) {
		return doOperation(jedis -> jedis.scard(key));
	}

}
