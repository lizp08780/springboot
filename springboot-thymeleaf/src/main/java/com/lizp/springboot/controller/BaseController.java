package com.lizp.springboot.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizp.springboot.util.ServletUtils;
import com.lizp.springboot.util.TableDataInfo;

public class BaseController {
	protected void startPage() {
		Integer pageNum = ServletUtils.getIntParameter("pageNum");
		Integer pageSize = ServletUtils.getIntParameter("pageSize");
		if (pageNum != null && pageSize != null) {
			String orderBy = ServletUtils.getStrParameter("orderByColumn");
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}
}
