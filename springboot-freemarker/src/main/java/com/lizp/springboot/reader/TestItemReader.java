package com.lizp.springboot.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;

public class TestItemReader implements ItemReader<String>, InitializingBean {

	@Override
	public String read() {
		return "read:" + System.currentTimeMillis();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("TestItemReader");
	}

}
