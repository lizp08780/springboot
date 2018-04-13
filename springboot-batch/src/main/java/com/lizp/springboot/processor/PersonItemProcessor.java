package com.lizp.springboot.processor;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.lizp.springboot.bean.entity.Person;

@Component
public class PersonItemProcessor implements ItemProcessor<Person, Person>, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);
	private AtomicLong count = new AtomicLong(0);

	@Override
	public Person process(Person person) {
		person.setId(count.incrementAndGet());
		person.setAge(12);
		person.setName("张三");
		return person;
	}

	@Override
	public void afterPropertiesSet() {
		logger.info("AuthItemProcessor init...");
	}

}
