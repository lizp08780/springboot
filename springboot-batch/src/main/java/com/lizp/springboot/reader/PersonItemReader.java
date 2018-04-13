package com.lizp.springboot.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.lizp.springboot.bean.entity.Person;

@Component
public class PersonItemReader implements ItemReader<Person> {

	@Override
	public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Person person = new Person();
		return person;
	}

}
