package com.gec.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ID;
	private String loginname;
	private String PASSWORD;
	private String username;
	private Integer STATUS;
	private Date createdate;
	public Integer getId() {
		return ID;
	}
	public void setId(Integer ID) {
		this.ID = ID;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return PASSWORD;
	}
	public void setPassword(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getStatus() {
		return STATUS;
	}
	public void setStatus(Integer STATUS) {
		this.STATUS = STATUS;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
