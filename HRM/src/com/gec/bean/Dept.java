package com.gec.bean;

/*
 *对应部门表的javabean
 * */
public class Dept implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer ID;		// id
	private String NAME;	// 部门名称
	private String REMARK;	// 详细描述
	private Integer state;
	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	// 无参数构造器
	public Dept() {
		super();
	}
	
	
	public Dept(Integer ID) {
		super();
		this.ID = ID;
	}


	public Dept(String NAME, String REMARK) {
		this.NAME = NAME;
		this.REMARK = REMARK;
	}
	public Dept(String NAME) {
		this.NAME = NAME;
	}
	// setter和getter方法
	public void setId(Integer ID){
		this.ID = ID;
	}
	public Integer getId(){
		return this.ID;
	}
	public void setName(String NAME){
		this.NAME = NAME;
	}
	public String getName(){
		return this.NAME;
	}
	public void setRemark(String REMARK){
		this.REMARK = REMARK;
	}
	public String getRemark(){
		return this.REMARK;
	}
	@Override
	public String toString() {
		return "Dept [id=" + ID + ", name=" + NAME + ", remark=" + REMARK + "]";
	}

}