package com.gec.service.impl;

import java.util.List;

import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.dao.impl.UserDaoImpl;
import com.gec.service.UserService;
import com.gec.util.PageModel;

public class UserServiceImpl implements UserService {
	UserDao ud = new UserDaoImpl();
	
	@Override
	public User login(String loginname, String password) {
		return ud.login(loginname, password);
	}
	
	@Override
	public User findById(Integer id) {
		return ud.findById(id);
	}
	
	@Override
	public boolean save(User entity) {
		return ud.save(entity);
	}
	
	@Override
	public boolean update(User entity) {
		return ud.update(entity);
	}
	
	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				ud.del(ids[i]);
				System.out.println(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public PageModel<User> findPage(int pageIndex, User entity) {
		return ud.findByPage(pageIndex, entity);
	}

	@Override
	public List<User> findAll() {
		return ud.findAll();
	}

}
