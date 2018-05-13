package com.lizp.springboot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.entity.Person;
import com.lizp.springboot.service.PersonService;

@RestController
public class IndexController {
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public Person index(Long id) {
		Person person = new Person();
		// person.setAge(age);
		// person.setName(name);
		person.setCreate(new Date());
		return person;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public Person find(Long id) {
		return personService.findById(id);
	}

	@RequestMapping(value = "/save")
	public Person add(Integer age, String name) {
		return personService.save(age, name);
	}
}
