package com.lizp.springboot.controller.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DruidController {

	// @RequiresPermissions("monitor:data:view")
	@GetMapping("/monitor/data")
	public String index() {
		return "redirect:/druid/index";
	}
}
