package com.entity;

public class User {
	private String id;
	private String number;
	private String username;
	private String password;
	private String name;
	private int sex;
	private String age;
	private String jobNum;
	private String jobName;
	private String phone;
	private int shiro;
	private String insert_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getShiro() {
		return shiro;
	}
	public void setShiro(int shiro) {
		this.shiro = shiro;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", number=" + number + ", username=" + username + ", password=" + password + ", name="
				+ name + ", sex=" + sex + ", age=" + age + ", jobNum=" + jobNum + ", jobName=" + jobName + ", phone="
				+ phone + ", shiro=" + shiro + ", insert_time=" + insert_time + "]";
	}
}
