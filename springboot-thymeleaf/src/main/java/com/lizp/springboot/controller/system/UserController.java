package com.lizp.springboot.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.controller.BaseController;
import com.lizp.springboot.domain.SysUser;
import com.lizp.springboot.persist.SysUserMapper;
import com.lizp.springboot.util.TableDataInfo;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
	private static String prefix = "system/user";
	@Autowired
	private SysUserMapper sysUserMapper;

	// @RequiresPermissions("system:user:view")
	@GetMapping()
	public String user() {
		return prefix + "/user";
	}

	// @RequiresPermissions("system:user:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysUser user) {
		startPage();
		List<SysUser> list = sysUserMapper.selectAll();
		return getDataTable(list);
	}
}
