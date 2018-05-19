package com.lizp.springboot.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tool/gen")
public class GenController {
	private static String prefix = "tool/gen";

	// @RequiresPermissions("tool:gen:view")
	@GetMapping()
	public String gen() {
		return prefix + "/gen";
	}

	// @RequiresPermissions("tool:gen:list")
//	@GetMapping("/list")
//	@ResponseBody
//	public TableDataInfo list(TableInfo tableInfo) {
//		startPage();
//		List<TableInfo> list = genService.selectTableList(tableInfo);
//		return getDataTable(list);
//	}
}
