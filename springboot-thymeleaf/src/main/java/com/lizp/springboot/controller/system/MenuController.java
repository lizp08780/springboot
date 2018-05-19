package com.lizp.springboot.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.controller.BaseController;
import com.lizp.springboot.domain.SysMenu;
import com.lizp.springboot.persist.SysMenuMapper;

@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

	private static String prefix = "system/menu";
	@Autowired
	private SysMenuMapper sysMenuMapper;

	// @RequiresPermissions("system:menu:view")
	@GetMapping()
	public String menu() {
		return prefix + "/menu";
	}

	// @RequiresPermissions("system:menu:list")
	@GetMapping("/list")
	@ResponseBody
	public List<SysMenu> list() {
		List<SysMenu> menuList = sysMenuMapper.selectAll();
		return menuList;
	}

	@GetMapping("/icon")
	public String icon() {
		return prefix + "/icon";
	}
}
