package com.lizp.springboot.controller;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Long id) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "ok";
	}

}
