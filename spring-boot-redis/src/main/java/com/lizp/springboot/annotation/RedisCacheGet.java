package com.lizp.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface RedisCacheGet {
	enum DataType {
		CLASS, JSON
	}

	String key();

	/**
	 * 缓存过期时间默认为不过期，过期时间手动去设定，单位为 S<br>
	 * 0:不限制保存时长
	 * 
	 * @return
	 */
	int expire() default 0;

	/**
	 * 数据类型
	 * 
	 * @return
	 */
	DataType dataType() default DataType.CLASS;
}
