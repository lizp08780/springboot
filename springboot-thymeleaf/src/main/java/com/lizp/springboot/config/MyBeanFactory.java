package com.lizp.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanFactory implements BeanPostProcessor {
	private static Logger logger = LoggerFactory.getLogger(MyBeanFactory.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		logger.info("对象---" + beanName + "---初始化开始");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		logger.info("对象---" + beanName + "---初始化成功");
		return bean;
	}

}
