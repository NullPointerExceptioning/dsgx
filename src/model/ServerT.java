package model;

/*
 * 客服表
 * serverId	客服id
 * user	用户名
 * pwd	密码
 * sex	性别
 * email	邮箱
 * nickName	昵称
 * age	年龄
 * rank	类型（等级）
 */
public class ServerT {
	private String serverId;
	private String user;
	private String pwd;
	private String sex;
	private String email;
	private String nickName;
	private String age;
	private String rank;
	
	public ServerT(){
		this.serverId = "";
		this.user = "";
		this.pwd = "";
		this.sex = "";
		this.email = "";
		this.nickName = "";
		this.age = "";
		this.rank = "";		
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	
	

}
