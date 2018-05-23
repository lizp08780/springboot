package com.lizp.springboot.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lizp.springboot.aspect.annotation.DistributedLock;
import com.lizp.springboot.lock.LockCallback;
import com.lizp.springboot.lock.LockTemplate;

@Aspect
@Component
public class DistributedLockAspect {
	@Autowired
	private LockTemplate lockTemplate;

	@Pointcut("@annotation(com.lizp.springboot.aspect.annotation.DistributedLock)")
	private void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
		Method method = methodName.getMethod();
		DistributedLock lock = method.getAnnotation(DistributedLock.class);
		return lockTemplate.execute(lock.lockNamePre() + lock.lockName() + lock.lockNamePost(), lock.waitTime(),
				lock.leaseTime(), new LockCallback<Object>() {

					@Override
					public Object doWithLock(boolean locked) {
						if (!locked) {
							return null;
						}
						return proceed(joinPoint);
					}
				});
	}

	@AfterThrowing(value = "pointcut()", throwing = "ex")
	public void afterThrowing(Throwable ex) {
		throw new RuntimeException(ex);
	}

	public Object proceed(ProceedingJoinPoint joinPoint) {
		try {
			return joinPoint.proceed();
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}
}
