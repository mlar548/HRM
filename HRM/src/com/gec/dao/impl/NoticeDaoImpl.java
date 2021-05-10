package com.gec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Notice;
import com.gec.dao.NoticeDao;
import com.gec.util.DBUtil;
import com.gec.util.PageModel;

public class NoticeDaoImpl extends DBUtil<Notice> implements NoticeDao {

	@Override
	public PageModel<Notice> findByPage(int pageIndex, Notice entity) {
		PageModel<Notice> pm = new PageModel<>();
		List<Object> obj = new ArrayList<>();
		String sql = "select count(id) from notice_inf where 1=1 ";
		String sql1 = "select * from notice_inf where 1=1 ";
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
		pm.setList(query(sql1, Notice.class, obj.toArray()));
 		return pm;
	}

	@Override
	public Notice findById(Integer id) {
		List<Notice> list = query("select * from notice_inf where id=?", Notice.class, id);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Notice entity) {
		return update("insert into notice_inf values(null,?,null,?,?,?,null)", entity.getName(), entity.getType_id(), entity.getContent(), entity.getUser_id());
	}

	@Override
	public boolean update(Notice entity) {
		return update("update notice_inf set name=?,type_id=?,content=?,user_id=?,modify_date=? where id=?", entity.getName(), entity.getType_id(), entity.getContent(), entity.getUser_id(), entity.getModifyDate(),entity.getId());
	}

	@Override
	public boolean del(String id) {
		if(id!=null&&!id.equals("")) {
			return update("delete from notice_inf where id=?", id);
		}else {
			return false;
		}
	}

	@Override
	public List<Notice> findAll() {
		return query("select * from notice_inf", Notice.class);
	}

}
