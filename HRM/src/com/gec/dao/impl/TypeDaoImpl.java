package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Type;
import com.gec.dao.TypeDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class TypeDaoImpl extends DBUtil<Type> implements TypeDao {

	@Override
	public PageModel<Type> findByPage(int pageIndex, Type entity) {
		PageModel<Type> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(id) from type_inf where 1=1 ";
		String sql1 = "select * from type_inf where 1=1 ";
		if(entity.getName()!=null&&!entity.getName().equals("")) {
			sql +="and name like ? ";
			sql1 +="and name like ? ";
			obj.add("%"+entity.getName()+"%");
		}
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		sql1 +="limit ?,?";
		obj.add(pm.getStartRow());
		obj.add(PageModel.getPagesize());
		pm.setList(query(sql1, Type.class, obj.toArray()));
 		return pm;
	}

	@Override
	public Type findById(Integer id) {
		List<Type> list = query("select * from type_inf where id=?", Type.class, id);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Type entity) {
		return update("insert into type_inf values(null,?,null,null,?,null)", entity.getName(), entity.getUser_id());
	}

	@Override
	public boolean update(Type entity) {
		return update("update type_inf set name=?,user_id=? where id=?", entity.getName(), entity.getUser_id(), entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from type_inf where id=?", id);
		}else {
			return false;
		}
	}

	@Override
	public List<Type> findAll() {
		return query("select * from type_inf", Type.class);
	}

}
