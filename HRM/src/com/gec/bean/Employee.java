package com.gec.bean;

import java.util.Date;

public class Employee {
	private Integer id;			// id
	private Dept dept;			// 部门
	private Integer dept_id;
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept.getId();
	}
	private Job job;			// 职位
	private Integer job_id;
	public Integer getJob_id() {
		return job_id;
	}
	public void setJob_id(Integer job_id) {
		this.job_id = job.getId();
	}
	private String name;		// 名称
	private String card_id;		// 身份证
	private String address;		// 地址
	private String post_code;	// 邮政编码
	private String tel;			// 电话
	private String phone;		// 手机
	private String qq_num;		// qq
	private String email;		// 邮箱
	private Integer sex;		// 性别
	private String party;		// 政治面貌
	
	private Date birthday;	//生日
	private String race;				// 名族
	private String education;			// 学历
	private String speciality;			// 专业
	private String hobby;				// 爱好
	private String remark;				// 备注
	private Integer state;
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	private Date create_date;	// 建档日期
	// 无参数构造器
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(String name, String cardId, String education, String email, String phone, String tel,
			String party, String qqNum, String address, String postCode, Date birthday, String race,
			String speciality, String hobby,Integer sex) {
		this.name = name;
		this.card_id = cardId;
		this.education = education;
		this.email = email;
		this.phone = phone;
		this.tel = tel;
		this.party = party;
		this.qq_num = qqNum;
		this.address = address;
		this.post_code = postCode;
		this.birthday = birthday;
		this.race = race;
		this.speciality = speciality;
		this.hobby = hobby;
		this.sex = sex;
	}
	// setter和getter方法
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}

	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setCardId(String cardId){
		this.card_id = cardId;
	}
	public String getCardId(){
		return this.card_id;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public void setPostCode(String postCode){
		this.post_code = postCode;
	}
	public String getPostCode(){
		return this.post_code;
	}
	public void setTel(String tel){
		this.tel = tel;
	}
	public String getTel(){
		return this.tel;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPhone(){
		return this.phone;
	}
	public void setQqNum(String qqNum){
		this.qq_num = qqNum;
	}
	public String getQqNum(){
		return this.qq_num;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setSex(Integer sex){
		this.sex = sex;
	}
	public Integer getSex(){
		return this.sex;
	}
	public void setParty(String party){
		this.party = party;
	}
	public String getParty(){
		return this.party;
	}
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	public java.util.Date getBirthday(){
		return this.birthday;
	}
	public void setRace(String race){
		this.race = race;
	}
	public String getRace(){
		return this.race;
	}
	public void setEducation(String education){
		this.education = education;
	}
	public String getEducation(){
		return this.education;
	}
	public void setSpeciality(String speciality){
		this.speciality = speciality;
	}
	public String getSpeciality(){
		return this.speciality;
	}
	public void setHobby(String hobby){
		this.hobby = hobby;
	}
	public String getHobby(){
		return this.hobby;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setCreateDate(java.util.Date createDate){
		this.create_date = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.create_date;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", dept=" + dept + ", job=" + job
				+ ", name=" + name + ", cardId=" + card_id + ", address="
				+ address + ", postCode=" + post_code + ", tel=" + tel
				+ ", phone=" + phone + ", qqNum=" + qq_num + ", email=" + email
				+ ", sex=" + sex + ", party=" + party + ", birthday="
				+ birthday + ", race=" + race + ", education=" + education
				+ ", speciality=" + speciality + 
				", hobby=" + hobby + ", remark=" + remark + ", createDate="
				+ create_date + "]";
	}

}
