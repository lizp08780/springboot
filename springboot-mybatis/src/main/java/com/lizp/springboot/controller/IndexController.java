package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.domain.Student;
import com.lizp.springboot.service.StudentService;

@RestController
public class IndexController {
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Long id) {
		Student student = new Student();
		student.setId(id);
		student.setAge(13);
		student.setName("test");
		studentService.insert(student);
		return "ok";
	}

}
