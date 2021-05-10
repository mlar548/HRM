package com.gec.bean;

public class Document {
	private int ID;					// 编号
	private String TITLE;			// 标题
	private String filename;		// 文件名
	private String filetype;        //文件类型
	private byte[] filebytes;       //文件内容
	private String REMARK;			// 描述
	private java.util.Date CREATE_DATE;	// 上传时间
	private int USER_ID;
	private User user;				// 上传人
	// 无参数构造器
	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}
	// setter和getter方法
	public void setId(int ID){
		this.ID = ID;
	}
	public int getId(){
		return this.ID;
	}
	public void setTitle(String TITLE){
		this.TITLE = TITLE;
	}
	public String getTitle(){
		return this.TITLE;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setRemark(String REMARK){
		this.REMARK = REMARK;
	}
	public String getRemark(){
		return this.REMARK;
	}
	public void setCreateDate(java.util.Date CREATEDATE){
		this.CREATE_DATE = CREATEDATE;
	}
	public java.util.Date getCreateDate(){
		return this.CREATE_DATE;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public byte[] getFileBytes() {
		return filebytes;
	}
	public void setFileBytes(byte[] fileBytes) {
		this.filebytes = fileBytes;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int USER_ID) {
		this.USER_ID = USER_ID;
	}
}
