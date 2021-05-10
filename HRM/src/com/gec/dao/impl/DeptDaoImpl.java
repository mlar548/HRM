package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.dao.DeptDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class DeptDaoImpl extends DBUtil<Dept> implements DeptDao {

	@Override
	public PageModel<Dept> findByPage(int pageIndex, Dept entity) {
		PageModel<Dept> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(ID) from dept_inf where 1=1 ";
		String sql1 ="select * from dept_inf where 1=1 ";
		if(entity.getName()!=null&&!entity.getName().equals("")) {
			sql +="and NAME like ? ";
			sql1 +="and NAME like ? ";
			obj.add("%"+entity.getName()+"%");
		}
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		sql1 += "limit ?,?";
		obj.add(pm.getStartRow());
		obj.add(PageModel.getPagesize());
		pm.setList(query(sql1, Dept.class, obj.toArray()));
		//System.out.println("查询语句:"+sql1);
		return pm;
	}

	@Override
	public Dept findById(Integer id) {
		List<Dept> list = query("select * from dept_inf where id=?", Dept.class, id);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public boolean save(Dept entity) {
		return update("insert into dept_inf values(null,?,?,null)", entity.getName(), entity.getRemark());
	}

	@Override
	public boolean update(Dept entity) {
		return update("update dept_inf set NAME=?,REMARK=? where ID=?", entity.getName(), entity.getRemark(), entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from dept_inf where ID=?", id);
		}else {
			return false;
		}	
	}

	@Override
	public List<Dept> findAll() {
		return query("select * from dept_inf", Dept.class);
	}


}
