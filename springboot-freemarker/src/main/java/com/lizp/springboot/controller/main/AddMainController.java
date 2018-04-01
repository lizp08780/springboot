package com.lizp.springboot.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.entity.main.Student;
import com.lizp.springboot.mapper.main.MainStudentDao;

@Controller
@RequestMapping(value = "/main")
public class AddMainController {
	@Autowired
	private MainStudentDao studentDao;

	@RequestMapping(value = "/add")
	@ResponseBody
	public Student add(String name, Integer age) {
		Student student = new Student();
		student.setName(name);
		student.setAge(age);
		studentDao.insert(student);
		return student;
	}
}
