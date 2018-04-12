package com.lizp.springboot.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;

public class TestItemProcessor implements ItemProcessor<String, String>, InitializingBean {

	@Override
	public String process(String arg0) {
		return "process:" + System.currentTimeMillis();
	}

	@Override
	public void afterPropertiesSet() {
		System.err.println("TestItemProcessor");
	}

}
