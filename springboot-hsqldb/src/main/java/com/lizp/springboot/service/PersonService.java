package com.lizp.springboot.service;

import com.lizp.springboot.entity.Person;

public interface PersonService {
	Person findById(Long id);

	Person save(Integer age, String name);
}
