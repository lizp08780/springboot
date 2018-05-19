package com.lizp.springboot.controller;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;
import com.lizp.springboot.annotation.Log;
import com.lizp.springboot.job.TestJob;
import com.lizp.springboot.persist.UserMapper;

@Controller
public class IndexController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@Transactional
	public String index(Model model) {
		userMapper.deleteByPrimaryKey(26);
		int x = 1 / 0;
		System.err.println(x);
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	@Log(desc = "测试记录日志")
	public String index2(Model model) {
		model.addAttribute("name", "Dear");
		PageHelper.startPage(3, 2);
		System.err.println(userMapper.selectAll());
		return "index2";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(TestJob.class).build();
		TriggerKey triggerKey = TriggerKey.triggerKey("001", Scheduler.DEFAULT_GROUP);
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();
		scheduler.scheduleJob(jobDetail, trigger);
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
		return "home";
	}

}
