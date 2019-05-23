package com.entity;

public class Work {
	private String id;
	private String number;
	private String name;
	private String info;
	private double money;
	private String userId;
	private String userName;
	private int status;
	private String jobNum;
	private String insert_time;
	private String file;
	private String why;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	@Override
	public String toString() {
		return "Work [id=" + id + ", number=" + number + ", name=" + name + ", info=" + info + ", money=" + money
				+ ", userId=" + userId + ", userName=" + userName + ", status=" + status + ", jobNum=" + jobNum
				+ ", insert_time=" + insert_time + ", file=" + file + ", why=" + why + "]";
	}
}
