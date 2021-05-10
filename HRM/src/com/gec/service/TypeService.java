package com.gec.service;

import java.util.List;

import com.gec.bean.Type;
import com.gec.util.PageModel;

public interface TypeService {
	//ID查询
	public Type findById(Integer id);
	//添加
	public boolean save(Type entity);
	//修改
	public boolean update(Type entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<Type> findPage(int pageIndex,Type entity);
	//查询所有
	public List<Type> findAll();
}
