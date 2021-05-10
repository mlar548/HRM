package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Employee;
import com.gec.dao.EmployeeDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class EmployeeDaoImpl extends DBUtil<Employee> implements EmployeeDao {

	@Override
	public PageModel<Employee> findByPage(int pageIndex, Employee entity) {
		 PageModel<Employee> pm = new PageModel<>();
		 List<Object> obj = new ArrayList<>();
		 String sql = "select count(id) from employee_inf where 1=1 ";
		 String sql1 = "select * from employee_inf where 1=1 ";
		 if(entity.getJob().getId()>0) {
			 sql += "and job_id=? ";
			 sql1 += "and job_id=? ";
			 obj.add(entity.getJob_id());
		 }
		 if(entity.getName()!=null&&!entity.getName().equals("")) {
			 sql += "and name like ? ";
			 sql1 += "and name like ? ";
			 obj.add("%"+entity.getName()+"%");
		 }
		 if(entity.getPhone()!=null&&!entity.getPhone().equals("")) {
			 sql += "and phone=? ";
			 sql1 += "and phone=? ";
			 obj.add(entity.getPhone());
		 }
		 if(entity.getCardId()!=null&&!entity.getCardId().equals("")) {
			 sql += "and card_id=? ";
			 sql1 += "and card_id=? ";
			 obj.add(entity.getCardId());
		 }
		 if(entity.getSex()!=-1) {
			 sql += "and sex=? ";
			 sql1 += "and sex=? ";
			 obj.add(entity.getSex());
		 }
		 if(entity.getDept().getId()>0) {
			 sql += "and dept_id=? ";
			 sql1 += "and dept_id=? ";
			 obj.add(entity.getDept_id());
		 }
		 pm.setPageIndex(pageIndex);
		 pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		 sql1 += "limit ?,? ";
//		 System.out.println("语句:"+sql);
//		 System.out.println("语句:"+sql1);
		 obj.add(pm.getStartRow());
		 obj.add(PageModel.getPagesize());
		 pm.setList(query(sql1, Employee.class, obj.toArray()));
		return pm;
 	}

	@Override
	public Employee findById(Integer id) {
		List<Employee> list = query("select * from employee_inf where id=?", Employee.class, id);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public boolean save(Employee entity) {
		List<Object> e = new ArrayList<>();
		String sql = "insert into employee_inf values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,?,?)";
		e.add(entity.getName());
		e.add(entity.getCardId());
		e.add(entity.getAddress());
		e.add(entity.getPostCode());
		e.add(entity.getTel());
		e.add(entity.getPhone());
		e.add(entity.getQqNum());
		e.add(entity.getEmail());
		e.add(entity.getSex());
		e.add(entity.getParty());
		e.add(entity.getBirthday());
		e.add(entity.getRace());
		e.add(entity.getEducation());
		e.add(entity.getSpeciality());
		e.add(entity.getHobby());
		e.add(entity.getRemark());
		e.add(entity.getDept_id());
		e.add(entity.getJob_id());
		return update(sql, e.toArray());
	}

	@Override
	public boolean update(Employee entity) {
		List<Object> e = new ArrayList<>();
		String sql = "update employee_inf set name=?,card_id=?,address=?,post_code=?,tel=?,phone=?,qq_num=?,email=?,sex=?,party=?,birthday=?,race=?,education=?,speciality=?,hobby=?,remark=?,dept_id=?,job_id=? where id=?";
		e.add(entity.getName());
		e.add(entity.getCardId());
		e.add(entity.getAddress());
		e.add(entity.getPostCode());
		e.add(entity.getTel());
		e.add(entity.getPhone());
		e.add(entity.getQqNum());
		e.add(entity.getEmail());
		e.add(entity.getSex());
		e.add(entity.getParty());
		e.add(entity.getBirthday());
		e.add(entity.getRace());
		e.add(entity.getEducation());
		e.add(entity.getSpeciality());
		e.add(entity.getHobby());
		e.add(entity.getRemark());
		e.add(entity.getDept_id());
		e.add(entity.getJob_id());
		e.add(entity.getId());
		return update(sql, e.toArray());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from employee_inf where id=?", id);
		}else {
			return false;
		}	
	}

	@Override
	public List<Employee> findAll() {
		return query("select * from employee_inf", Employee.class);
	}

}
