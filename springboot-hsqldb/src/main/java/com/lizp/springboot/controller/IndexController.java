package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.dao.PersonDao;
import com.lizp.springboot.entity.Person;

@RestController
public class IndexController {

	@Autowired
	private PersonDao personDao;

	@RequestMapping(value = "/index")
	public Person index(Long id) {
		return personDao.findById(id).get();
	}

	@RequestMapping(value = "/save")
	public Person add(Integer age, String name) {
		return save(age, name);
	}

	private Person save(Integer age, String name) {
		Person person = new Person();
		person.setAge(age);
		person.setName(name);
		return personDao.save(person);
	}

}
