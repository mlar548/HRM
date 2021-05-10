package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Job;
import com.gec.dao.JobDao;
import com.gec.dao.impl.JobDaoImpl;
import com.gec.service.JobService;
import com.gec.util.PageModel;

public class JobServiceImpl implements JobService {
	JobDao jd = new JobDaoImpl();

	@Override
	public Job findById(Integer id) {
		return jd.findById(id);
	}

	@Override
	public boolean save(Job entity) {
		return jd.save(entity);
	}

	@Override
	public boolean update(Job entity) {
		return jd.update(entity);
	}

	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				jd.del(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public PageModel<Job> findPage(int pageIndex, Job entity) {
		return jd.findByPage(pageIndex, entity);
	}

	@Override
	public List<Job> findAll() {
		return jd.findAll();
	}

}
