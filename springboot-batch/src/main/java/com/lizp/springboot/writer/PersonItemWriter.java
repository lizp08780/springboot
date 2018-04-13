package com.lizp.springboot.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.lizp.springboot.bean.entity.Person;

@Component
public class PersonItemWriter implements ItemWriter<Person>, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("PersonItemWriter init...");

	}

	@Override
	public void write(List<? extends Person> items) throws Exception {
		System.err.println(items.size());
		System.err.println(items);
	}

}
