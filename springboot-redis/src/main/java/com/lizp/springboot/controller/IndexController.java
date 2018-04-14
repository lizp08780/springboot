package com.lizp.springboot.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.domain.Student;
import com.lizp.springboot.service.StudentService;

@RestController
public class IndexController {
	@Autowired
	private StudentService studentService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		Student student = new Student();
		student.setAge(13);
		student.setName("test");
		studentService.insert(student);
		return "ok";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(Long id) {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		String key = "student_" + id;
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			String student = operations.get(key);
			return student;
		} else {
			operations.set(key, "hehe" + id, 20, TimeUnit.SECONDS);
		}
		return "unkonw";
	}

}
