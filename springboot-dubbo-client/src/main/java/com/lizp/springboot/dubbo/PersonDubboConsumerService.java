package com.lizp.springboot.dubbo;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizp.springboot.domain.Person;

@Component
public class PersonDubboConsumerService {

	@Reference(version = "1.0.0")
	PersonDubboService personDubboService;

	public void printPerson() {
		Person person = personDubboService.findById(1L);
		System.out.println(person);
	}
}
