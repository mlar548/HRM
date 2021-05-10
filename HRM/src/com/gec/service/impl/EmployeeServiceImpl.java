package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Employee;
import com.gec.dao.EmployeeDao;
import com.gec.dao.impl.EmployeeDaoImpl;
import com.gec.service.EmployeeService;
import com.gec.util.PageModel;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDao ed = new EmployeeDaoImpl();
	
	@Override
	public Employee findById(Integer id) {
		return ed.findById(id);
	}

	@Override
	public boolean save(Employee entity) {
		return ed.save(entity);
	}

	@Override
	public boolean update(Employee entity) {
		return ed.update(entity);
	}

	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				ed.del(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public PageModel<Employee> findPage(int pageIndex, Employee entity) {
		return ed.findByPage(pageIndex, entity);
	}

	@Override
	public List<Employee> findAll() {
		return ed.findAll();
	}

}
