package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Document;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImpl;
import com.gec.service.DocumentService;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class DocumentServiceImpl extends DBUtil<Document> implements DocumentService {
	DocumentDao dmd = new DocumentDaoImpl(); 
	@Override
	public Document findById(Integer id) {
		return dmd.findById(id);
	}

	@Override
	public boolean save(Document entity) {
		return dmd.save(entity);
	}

	@Override
	public boolean update(Document entity) {
		return dmd.update(entity);
	}

	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				dmd.del(ids[i]);
				//System.out.println(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public PageModel<Document> findPage(int pageIndex, Document entity) {
		return dmd.findByPage(pageIndex, entity);
	}

	@Override
	public List<Document> findAll() {
		return dmd.findAll();
	}

}
