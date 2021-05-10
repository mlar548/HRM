package com.gec.service.impl;

import com.gec.bean.Notice;
import com.gec.dao.NoticeDao;
import com.gec.dao.impl.NoticeDaoImpl;
import com.gec.service.NoticeService;
import com.gec.util.PageModel;

public class NoticeServiceImpl implements NoticeService {
	NoticeDao nd = new NoticeDaoImpl();	

	@Override
	public Notice findById(Integer id) {
		return nd.findById(id);
	}

	@Override
	public boolean save(Notice entity) {
		return nd.save(entity);
	}

	@Override
	public boolean update(Notice entity) {
		return nd.update(entity);
	}

	@Override
	public boolean del(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				nd.del(ids[i]);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public PageModel<Notice> findPage(int pageIndex, Notice entity) {
		return nd.findByPage(pageIndex, entity);
	}

}
