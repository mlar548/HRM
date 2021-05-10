package com.gec.bean;

import java.util.Date;

public class Notice {
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


	public Integer getType_id() {
		return type_id;
	}


	public void setType_id(Integer type_id) {
		this.type_id = type.getId();
	}


	private Date create_date;
	private Date modify_date;
	private Type type;
	private Integer type_id;
	private String content;
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


	public Notice(String name) {
		this.name = name;
	}


	public Notice() {
		super();
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Notice(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}


	@Override
	public String toString() {
		return "Notice [id=" + id + ", name=" + name + ", user=" + user + ", createDate=" + create_date + ", modifyDate="
				+ modify_date + ", type=" + type + ", content=" + content + "]";
	}


}
