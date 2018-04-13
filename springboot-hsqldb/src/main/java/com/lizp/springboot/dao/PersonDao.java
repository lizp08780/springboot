package com.lizp.springboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.lizp.springboot.entity.Person;

public interface PersonDao extends CrudRepository<Person, Long> {

}
