package com.lizp.springboot.shiro;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class PasswordRetry implements Serializable {

	private static final long serialVersionUID = 6082838389727186387L;
	private AtomicInteger retryCount = new AtomicInteger(0);
	private long lastRetryTime = System.currentTimeMillis();

	public int incrementAndGet(int lockTime) {
		int count = retryCount.incrementAndGet();
		long now = System.currentTimeMillis();
		// 超过锁定时间，解锁
		if (now - lockTime > lastRetryTime) {
			lastRetryTime = now;
			return 0;
		} else {
			lastRetryTime = now;
			return count;
		}
	}
}
