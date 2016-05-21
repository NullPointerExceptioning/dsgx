package model;

import javax.servlet.http.HttpServletRequest;

/*
 * 用户信息表
 * userId	用户id
 * user	用户名
 * pwd	登录密码
 * phone	联系电话
 * email	邮箱
 * local	所在地
 * sex	性别
 * age	年龄
 * nickName	昵称
 * birthday	生日
 */
public class UserT {
	private String userId;
	private String user;
	private String pwd;
	private String phone;
	private String email;
	private String local;
	private String sex;
	private String age;
	private String nickName;
	private String birthday;
	
	public UserT(){
		this.userId = "";
		this.user = "";
		this.pwd ="";
		this.phone = "";
		this.email = "";
		this.local = "";
		this.sex = "";
		this.age = "";
		this.nickName = "";
		this.birthday = "";
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	

}
