package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class UserDaoImpl extends DBUtil<User> implements UserDao {

	@Override
	public PageModel<User> findByPage(int pageIndex, User entity) {
		PageModel<User> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(ID) from user_inf where 1=1 ";
		String sql1 ="select * from user_inf where 1=1 ";
		if(entity.getStatus()!=0) {
			sql +="and STATUS=? ";
			sql1 +="and STATUS=? ";
			obj.add(entity.getStatus());
		}
		if(entity.getLoginname()!=null&&!entity.getLoginname().equals("")) {
			sql +="and loginname like ? ";
			sql1 +="and loginname like ? ";
			obj.add("%"+entity.getLoginname()+"%");
		}
		if(entity.getUsername()!=null&&!entity.getUsername().equals("")) {
			sql +="and username like ? ";
			sql1 +="and username like ? ";
			obj.add("%"+entity.getUsername()+"%");
		}
		//System.out.println("dao层  登录名:"+entity.getLoginname()+"   用户名"+entity.getUsername()+"  状态:"+entity.getStatus());
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		sql1 += "limit ?,?";
		obj.add(pm.getStartRow());
		obj.add(PageModel.getPagesize());
		pm.setList(query(sql1, User.class, obj.toArray()));
		//System.out.println("查询语句:"+sql1);
		return pm;
	}

	@Override
	public User findById(Integer id) {
		List<User> list = query("select * from user_inf where id=?", User.class, id);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public boolean save(User entity) {
		return update("insert into user_inf values(null,?,?,?,null,?)", entity.getLoginname(), entity.getPassword(), entity.getStatus(), entity.getUsername());
	}

	@Override
	public boolean update(User entity) {
		return update("update user_inf set loginname=?,PASSWORD=?,STATUS=?,username=? where ID=?", entity.getLoginname(), entity.getPassword(), entity.getStatus(), entity.getUsername(), entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from user_inf where ID=?", id);
		}else {
			return false;
		}	
	}

	@Override
	public List<User> findAll() {
		return query("select * from user_inf", User.class);
	}

	@Override
	public User login(String loginname, String password) {
		List<User> list = query("select * from user_inf where loginname=? and password=?", User.class, loginname, password);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

}
