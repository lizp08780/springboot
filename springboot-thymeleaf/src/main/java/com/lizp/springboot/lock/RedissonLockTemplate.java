package com.lizp.springboot.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class RedissonLockTemplate extends LockTemplate implements InitializingBean {
	private Logger log = LoggerFactory.getLogger(RedissonLockTemplate.class);
	private RedissonClient redissonClient;

	public RedissonLockTemplate(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	public RedissonClient getRedissonClient() {
		return redissonClient;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redissonClient, "redissonClient can't be null");
	}

	@Override
	public <T> T execute(String key, long waitTime, long leaseTime, LockCallback<T> callback) {
		RLock lock = redissonClient.getLock(key);
		boolean locked = true;
		log.debug("Try to lock {}", key);

		// 不设置waitTime和leaseTime
		if (waitTime <= 0 && leaseTime <= 0) {
			lock.lock();
		} // 只设置waitTime
		else if (waitTime > 0 && leaseTime <= 0) {
			try {
				if (!lock.tryLock(waitTime, TimeUnit.MILLISECONDS)) {
					locked = false;
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		} // 只设置leaseTime
		else if (leaseTime > 0 && waitTime <= 0) {
			lock.lock(leaseTime, TimeUnit.MILLISECONDS);
		} else {// waitTime和leaseTime都设置
			try {
				if (!lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS)) {
					locked = false;
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		if (locked) {
			log.debug("Lock {} SUCCESS", key);
		}

		try {
			return callback.doWithLock(locked);
		} finally {
			if (locked) {
				log.debug("Unlock {} ", key);
				lock.unlock();
				log.debug("Unlock {} SUCCESS", key);
			}
		}
	}
}
