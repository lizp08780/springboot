package com.lizp.springboot.lock;

public interface LockCallback<T> {
	/**
	 * 执行业务逻辑
	 * 
	 * @param locked
	 *            是否加锁成功
	 * @return
	 */
	T doWithLock(boolean locked);
}
