package com.lizp.springboot.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.controller.BaseController;
import com.lizp.springboot.domain.SysRole;
import com.lizp.springboot.persist.SysRoleMapper;
import com.lizp.springboot.util.TableDataInfo;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
	private static String prefix = "system/role";
	@Autowired
	private SysRoleMapper sysRoleMapper;

	// @RequiresPermissions("system:role:view")
	@GetMapping()
	public String role() {
		return prefix + "/role";
	}

	// @RequiresPermissions("system:role:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysRole role) {
		startPage();
		List<SysRole> list = sysRoleMapper.selectAll();
		return getDataTable(list);
	}
}
