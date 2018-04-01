package com.lizp.springboot.controller.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.entity.temp.Student;
import com.lizp.springboot.mapper.temp.TempStudentDao;

@Controller
@RequestMapping(value = "/temp")
public class AddTempController {
	@Autowired
	private TempStudentDao studentDao;

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
