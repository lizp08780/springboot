package com.lizp.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lizp.springboot.dubbo.PersonDubboConsumerService;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ClientApplication.class, args);
		PersonDubboConsumerService personService = run.getBean(PersonDubboConsumerService.class);
		personService.printPerson();
	}
}
