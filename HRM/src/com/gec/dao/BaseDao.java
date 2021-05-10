package com.gec.dao;

import java.util.List;

import com.gec.util.PageModel;

public interface BaseDao<T> {
	//分页查询
	public PageModel<T> findByPage(int pageIndex, T entity);
	//根据ID查询
	public T findById(Integer id);
	//保存
	public boolean save(T entity);
	//更新
	public boolean update(T entity);
	//删除
	public boolean del(String id);
	//查询所有
	public List<T> findAll();
}
