package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Document;
import com.gec.dao.DocumentDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class DocumentDaoImpl extends DBUtil<Document> implements DocumentDao {

	@Override
	public PageModel<Document> findByPage(int pageIndex, Document entity) {
		PageModel<Document> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(ID) from document_inf where 1=1 ";
		String sql1 = "select * from document_inf where 1=1 ";
		if(entity.getTitle()!=null&&!entity.getTitle().equals("")) {
			sql +="and TITLE like ? ";
			sql1 +="and TITLE like ? ";
			obj.add("%"+entity.getTitle()+"%");
		}
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(getCount(sql, obj.toArray()));
		sql1 +="limit ?,?";
		obj.add(pm.getStartRow());
		obj.add(PageModel.getPagesize());
		pm.setList(query(sql1, Document.class, obj.toArray()));
 		return pm;
	}

	@Override
	public Document findById(Integer id) {
		List<Document> list = query("select * from document_inf where ID=?", Document.class, id);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Document entity) {
		return update("insert into document_inf values(null,?,?,?,?,?,null,?)", entity.getTitle(), entity.getFilename(), entity.getFiletype(), entity.getFileBytes(), entity.getRemark(), entity.getUSER_ID());
	}

	@Override
	public boolean update(Document entity) {
		return update("update document_inf set TITLE=?,filename=?,filetype=?,filebytes=?,REMARK=? where ID=?",entity.getTitle(),entity.getFilename(),entity.getFiletype(),entity.getFileBytes(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from document_inf where ID=?", id);
		}else {
			return false;
		}
	}

	@Override
	public List<Document> findAll() {
		return query("select * from document_inf", Document.class);
	}

}
