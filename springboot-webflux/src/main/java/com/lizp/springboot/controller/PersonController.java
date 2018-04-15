package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.dao.PersonDao;
import com.lizp.springboot.entity.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {
	@Autowired
	private PersonDao personDao;

	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public Mono<Person> createPerson(Person person) {
		return Mono.create(cityMonoSink -> cityMonoSink.success(personDao.save(person)));
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public Mono<Person> findOnePerson(@PathVariable("id") Long id) {
		return Mono.create(cityMonoSink -> cityMonoSink.success(personDao.findById(id).get()));
	}

	@RequestMapping(value = "/person", method = RequestMethod.PUT)
	public Mono<Person> modifyCity(Person person) {
		return Mono.create(cityMonoSink -> cityMonoSink.success(personDao.save(person)));
	}

	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public Flux<Person> findAllPerson() {
		return Flux.create(cityFluxSink -> {
			personDao.findAll().forEach(person -> {
				cityFluxSink.next(person);
			});
			cityFluxSink.complete();
		});
	}
}
