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
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		Student student = new Student();
		student.setAge(13);
		student.setName("test");
		studentService.insert(student);
		return "ok";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Student get(Long id) {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		String key = "student_" + id;
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			Student student = (Student) operations.get(key);
			return student;
		} else {
			Student student = new Student();
			student.setId(id);
			student.setAge(13);
			student.setName("test");
			operations.set(key, student, 20, TimeUnit.SECONDS);
		}
		return null;
	}

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public Student cache(Long id) {
		Student student = studentService.getById(id);
		System.err.println(student);
		return student;
	}

}
