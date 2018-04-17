package com.lizp.springboot.reader;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.lizp.springboot.entity.Person;

@Component
public class PersonItemReader implements ItemReader<Person> {
	// private static Logger logger =
	// LoggerFactory.getLogger(PersonItemReader.class);
	private AtomicLong count = new AtomicLong(0);

	@Override
	public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count.get() >= 1000) {
			return null;
		}
		Person person = new Person();
		person.setId(count.incrementAndGet());
		return person;
	}

}
