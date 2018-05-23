package com.lizp.springboot.service;

import org.springframework.cache.annotation.Cacheable;

import com.lizp.springboot.domain.Student;

public interface StudentService {
	int insert(Student student);

	@Cacheable(value = "testCache", key = "#id")
	Student getById(Long id);
}
