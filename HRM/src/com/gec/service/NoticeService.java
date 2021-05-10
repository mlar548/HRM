package com.gec.service;

import com.gec.bean.Notice;
import com.gec.util.PageModel;

public interface NoticeService {
	//ID查询
	public Notice findById(Integer id);
	//添加
	public boolean save(Notice entity);
	//修改
	public boolean update(Notice entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<Notice> findPage(int pageIndex,Notice entity);
}
