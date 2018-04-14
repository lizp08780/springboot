package com.lizp.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lizp.springboot.dao.StudentDao;
import com.lizp.springboot.domain.Student;
import com.lizp.springboot.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;

	@Override
	public int insert(Student student) {
		return studentDao.insert(student);
	}
}
