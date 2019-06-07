package com.lizp.springboot.controller;

import com.lizp.exception.BusinessException;
import com.lizp.springboot.dao.PersonDao;
import com.lizp.springboot.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class PersonController {
    @Autowired
    private PersonDao personDao;

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public Mono<Person> createPerson(@Valid @RequestBody Person person, BindingResult result) {
        if (result.hasErrors()) {
            return Mono.error(new BusinessException(result.getFieldError().getDefaultMessage()));
        }
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
