package com.gec.bean;

import java.util.Date;

public class Type {
	private Integer id;
	private String name;
	private User user;
	private Integer user_id;
	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}


	private Date create_date;
	private Date modify_date;
	private Integer state;

	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getCreateDate() {
		return create_date;
	}


	public void setCreateDate(Date createDate) {
		this.create_date = createDate;
	}


	public Date getModifyDate() {
		return modify_date;
	}


	public void setModifyDate(Date modifyDate) {
		this.modify_date = modifyDate;
	}


	public Type(String name) {
		this.name = name;
	}


	public Type() {
		super();
	}


	public Type(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Notice [id=" + id + ", name=" + name + ", user=" + user + ", createDate=" + create_date + ", modifyDate="
				+ modify_date+ "]";
	}
	
	

}
