package com.lizp.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.lizp.springboot.domain.Student;
import com.lizp.springboot.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Override
	public int insert(Student student) {
		return 1;
	}

	@Override
	public Student getById(Long id) {
		Student student = new Student();
		student.setId(id);
		student.setAge(123);
		student.setName("test");
		System.err.println("没有使用缓存：" + student);
		return student;
	}
}
