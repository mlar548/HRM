package com.gec.service;

import java.util.List;

import com.gec.bean.User;
import com.gec.util.PageModel;

public interface UserService {
	//登录
	public User login(String loginname, String password);
	//ID查询
	public User findById(Integer id);
	//添加
	public boolean save(User entity);
	//修改
	public boolean update(User entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<User> findPage(int pageIndex,User entity);
	//查询所有
	public List<User> findAll();
}
