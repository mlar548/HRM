package com.gec.service;

import java.util.List;

import com.gec.bean.Document;
import com.gec.util.PageModel;

public interface DocumentService {
	//ID查询
	public Document findById(Integer id);
	//添加
	public boolean save(Document entity);
	//修改
	public boolean update(Document entity);
	//删除
	public boolean del(String[] ids);
	//页面
	public PageModel<Document> findPage(int pageIndex,Document entity);
	//查询所有
	public List<Document> findAll();
}
