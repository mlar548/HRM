package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Job;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class JobDaoImpl extends DBUtil<Job> implements com.gec.dao.JobDao {

	@Override
	public PageModel<Job> findByPage(int pageIndex, Job entity) {
		PageModel<Job> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(id) from job_inf where 1=1 ";
		String sql1 = "select * from job_inf where 1=1 ";
		if(entity.getName()!=null&&!entity.getName().equals("")) {
			sql +="and name like ? ";
			sql1 +="and name like ? ";
			obj.add("%"+entity.getName()+"%");
		}
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		sql1 += "limit ?,?";
		obj.add(pm.getStartRow());
		obj.add(PageModel.getPagesize());
		pm.setList(query(sql1, Job.class, obj.toArray()));
 		return pm;
	}

	@Override
	public Job findById(Integer id) {
		List<Job> list = query("select * from job_inf where id=?", Job.class, id);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Job entity) {
		return update("insert into job_inf values(null,?,?,null)", entity.getName(), entity.getRemark());
	}

	@Override
	public boolean update(Job entity) {
		return update("update job_inf set name=?,remark=? where id=?", entity.getName(), entity.getRemark(),entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from job_inf where id=?", id);
		}else {
			return false;
		}
	}

	@Override
	public List<Job> findAll() {
		return query("select * from job_inf", Job.class);
	}

}
