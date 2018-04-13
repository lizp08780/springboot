package com.lizp.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lizp.springboot.dao.PersonDao;
import com.lizp.springboot.entity.Person;
import com.lizp.springboot.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonDao personDao;

	@Override
	public Person findById(Long id) {
		return personDao.findById(id).get();
	}

	@Override
	public Person save(Integer age, String name) {
		Person person = new Person();
		person.setAge(age);
		person.setName(name);
		return personDao.save(person);
	}

}
