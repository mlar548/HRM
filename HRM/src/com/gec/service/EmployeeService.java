package com.gec.service;

import java.util.List;

import com.gec.bean.Employee;
import com.gec.util.PageModel;

public interface EmployeeService {
	//ID查询
	public Employee findById(Integer id);
	//添加
	public boolean save(Employee entity);
	//修改
	public boolean update(Employee entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<Employee> findPage(int pageIndex,Employee entity);
	//查询所有
	public List<Employee> findAll();
}
