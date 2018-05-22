package com.lizp.springboot.lock;

public abstract class LockCallbackWithoutResult implements LockCallback<Object> {
	@Override
	public Object doWithLock(boolean locked) {
		doWithLockWithoutResult(locked);
		return null;
	}

	protected abstract void doWithLockWithoutResult(boolean locked);
}
