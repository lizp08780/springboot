package com.lizp.springboot.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {
	/**
	 * 锁的名称
	 * 
	 * @return
	 */
	String lockName();

	/**
	 * lockName前缀
	 * 
	 * @return
	 */
	String lockNamePre() default "lock:";

	/**
	 * lockName后缀
	 * 
	 * @return
	 */
	String lockNamePost() default "";

	/**
	 * 最长等待时间，默认-1，一直等待
	 * 
	 * @return
	 */
	long waitTime() default -1L;

	/**
	 * 锁超时时间,超时时间过后，锁自动释放<br>
	 * 默认-1，执行完毕才释放
	 * 
	 * @return
	 */
	long leaseTime() default -1L;

}
