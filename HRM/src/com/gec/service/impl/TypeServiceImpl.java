package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Type;
import com.gec.dao.TypeDao;
import com.gec.dao.impl.TypeDaoImpl;
import com.gec.service.TypeService;
import com.gec.util.PageModel;

public class TypeServiceImpl implements TypeService {
	TypeDao td = new TypeDaoImpl();	

	@Override
	public Type findById(Integer id) {
		return td.findById(id);
	}

	@Override
	public boolean save(Type entity) {
		return td.save(entity);
	}

	@Override
	public boolean update(Type entity) {
		return td.update(entity);
	}

	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				td.del(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public PageModel<Type> findPage(int pageIndex, Type entity) {
		return td.findByPage(pageIndex, entity);
	}

	@Override
	public List<Type> findAll() {
		return td.findAll();
	}

}
