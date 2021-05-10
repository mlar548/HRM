package com.gec.dao;

import com.gec.bean.User;

public interface UserDao extends BaseDao<User> {
	
	public User login(String username, String password);
}
