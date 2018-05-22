package com.lizp.springboot.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizp.springboot.aspect.annotation.Log;
import com.lizp.springboot.aspect.annotation.Log.LOG_TYPE;
import com.lizp.springboot.domain.SysDept;
import com.lizp.springboot.persist.SysDeptMapper;

@Controller
@RequestMapping("/system/dept")
public class DeptController {
	private static final String prefix = "system/dept";

	@Autowired
	private SysDeptMapper sysDeptMapper;

	//@RequiresPermissions("system:dept:view")
	@GetMapping()
	public String dept() {
		return prefix + "/dept";
	}

	// @RequiresPermissions("system:dept:list")
	@GetMapping("/list")
	@ResponseBody
	public List<SysDept> list() {
		List<SysDept> deptList = sysDeptMapper.selectAll();
		return deptList;
	}

	@Log(type = LOG_TYPE.ADD, desc = "部门管理-新增部门")
	// @RequiresPermissions("system:dept:add")
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Integer parentId, Model model) {
		SysDept dept = sysDeptMapper.selectByPrimaryKey(parentId);
		model.addAttribute("dept", dept);
		return prefix + "/add";
	}
}
