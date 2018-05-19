package com.lizp.springboot.controller;

import java.util.List;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lizp.springboot.annotation.Log;
import com.lizp.springboot.domain.SysMenu;
import com.lizp.springboot.domain.SysUser;
import com.lizp.springboot.job.TestJob;
import com.lizp.springboot.persist.SysMenuMapper;
import com.lizp.springboot.persist.SysUserMapper;

@Controller
public class IndexController {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserMapper sysUserMapper;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		SysMenu sysMenu = new SysMenu();
		sysMenu.setParentId(0);
		List<SysMenu> parentMenus = sysMenuMapper.select(sysMenu);
		for (SysMenu sysMenu2 : parentMenus) {
			sysMenu.setParentId(sysMenu2.getMenuId());
			List<SysMenu> childrenMenus = sysMenuMapper.select(sysMenu);
			sysMenu2.setChildren(childrenMenus);
		}

		model.addAttribute("menus", parentMenus);
		SysUser user = sysUserMapper.selectByPrimaryKey(1);
		model.addAttribute("user", user);
		model.addAttribute("user.dept.deptName", "123");
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	@Log(desc = "测试记录日志")
	public String index2(Model model) {
		model.addAttribute("name", "Dear");
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
