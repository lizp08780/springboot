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
	public Person index() {
		return personDao.findById(1L).get();
	}

	private void save() {
		Person person = new Person();
		person.setAge(18);
		person.setName("如花");
		personDao.save(person);
	}

}
