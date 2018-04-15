package com.lizp.springboot.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lizp.springboot.domain.Person;
import com.lizp.springboot.dubbo.PersonDubboService;

@Service(version = "1.0.0")
public class PersonDubboServiceImpl implements PersonDubboService {

	public Person findById(Long id) {
		return new Person(1L, "zhangsan", 100);
	}
}
