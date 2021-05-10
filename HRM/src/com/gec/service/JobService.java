package com.gec.service;

import java.util.List;

import com.gec.bean.Job;
import com.gec.util.PageModel;

public interface JobService {
		//ID查询
		public Job findById(Integer id);
		//添加
		public boolean save(Job entity);
		//修改
		public boolean update(Job entity);
		//删除
		public boolean del(String[] ids);
		//查询所有
		public List<Job> findAll();
		//页面
		public PageModel<Job> findPage(int pageIndex,Job entity);
}
