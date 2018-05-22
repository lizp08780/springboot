package com.lizp.springboot.lock;

public abstract class LockTemplate {
	/**
	 * 加锁下执行操作
	 * 
	 * @param key
	 *            锁关键字
	 * @param waitTime
	 *            等待锁时间
	 * @param leaseTime
	 *            锁释放时间
	 * @param callback
	 *            回调
	 * @return
	 */
	public abstract <T> T execute(String key, long waitTime, long leaseTime, LockCallback<T> callback);

	/**
	 * 
	 * @param key
	 * @param waitTime
	 * @param callback
	 * @return
	 */
	public <T> T execute(String key, long waitTime, LockCallback<T> callback) {
		return execute(key, waitTime, -1, callback);
	}

	/**
	 * 
	 * @param key
	 * @param callback
	 * @return
	 */
	public <T> T execute(String key, LockCallback<T> callback) {
		return execute(key, -1, -1, callback);
	}
}
