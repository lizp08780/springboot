package com.lizp.springboot.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
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
	public void pointcut() {
	}

	@Around(value = "pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
		Method method = methodName.getMethod();
		Object[] arguments = joinPoint.getArgs();
		DistributedLock lock = method.getAnnotation(DistributedLock.class);
		String lockKey = lock.lockNamePre() + getLockName(lock.lockName(), arguments) + lock.lockNamePost();

		return lockTemplate.execute(lockKey, lock.waitTime(), lock.leaseTime(), new LockCallback<Object>() {

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

	private String getLockName(String[] lockName, Object[] args) {
		StringBuilder sb = new StringBuilder();
		for (String value : lockName) {
			String[] argName = StringUtils.split(value, ".");
			int argNo = Integer.parseInt(argName[0]);
			if (argName.length == 2) {
				String lock = getValue(args[argNo], argName[1]);
				sb.append(lock).append(":");
			} else {
				sb.append(args[argNo]).append(":");
			}
		}
		return sb.toString();
	}

	private String getValue(Object arg, String name) {
		for (Field field : arg.getClass().getDeclaredFields()) {
			field.setAccessible(true);// 设置有访问权限
			if (field.getName().equals(name)) {
				try {
					return "" + field.get(arg);
				} catch (Exception e) {
					throw new IllegalArgumentException("设置锁名称错误，没有从参数中查到对应的属性名");
				}
			}
		}
		return "";
	}
}
