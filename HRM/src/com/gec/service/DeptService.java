package com.gec.service;

import java.util.List;

import com.gec.bean.Dept;
import com.gec.util.PageModel;

public interface DeptService {
	//ID查询
	public Dept findById(Integer id);
	//添加
	public boolean save(Dept entity);
	//修改
	public boolean update(Dept entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<Dept> findPage(int pageIndex,Dept entity);
	//查询所有
	public List<Dept> findAll();
}
