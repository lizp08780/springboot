package com.lizp.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.lizp.springboot.domain.Student;
import com.lizp.springboot.service.StudentService;

@RestController
public class IndexController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RestTemplate restTemplate;
	private static final HttpHeaders headers;
	static {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(list);
	}

	public <T> ResponseEntity<T> post_json(String url, Object request, Class<T> responseType) {
		HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
		return restTemplate.postForEntity(url, entity, responseType);

	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.err.println(restTemplate
				.getForEntity("http://60.190.227.165:8090/dev204/api_v3/pay/unionFrontUrl", String.class).getBody());

		Student student = new Student();
		student.setAge(13);
		student.setName("test你好");
		studentService.insert(student);
		System.err.println(restTemplate.postForObject("http://10.40.128.31:8010/bank/pay", student, String.class));
		System.err.println(post_json("http://10.40.128.31:8010/bank/pay", student, String.class).getBody());
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
