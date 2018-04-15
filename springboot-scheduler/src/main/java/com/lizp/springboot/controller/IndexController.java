package com.lizp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lizp.springboot.task.AsyncTask;

@RestController
public class IndexController {
	@Autowired
	private AsyncTask asyncTask;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		long begin = System.currentTimeMillis();
		asyncTask.task1();
		long end = System.currentTimeMillis();
		return "task任务总耗时:" + (end - begin) + "ms";
	}

}
