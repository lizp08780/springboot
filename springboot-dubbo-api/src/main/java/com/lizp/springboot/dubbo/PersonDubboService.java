package com.lizp.springboot.dubbo;

import com.lizp.springboot.domain.Person;

public interface PersonDubboService {

	Person findById(Long id);
}
