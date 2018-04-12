package com.lizp.springboot.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

public class TestItemWriter<T> implements ItemWriter<T>, InitializingBean {

	@Override
	public void afterPropertiesSet() {
		System.err.println("TestItemWriter");
	}

	@Override
	public void write(List<? extends T> items) throws Exception {
		System.err.println("begin");
		System.err.println(items);
		System.err.println("end");
	}

}
