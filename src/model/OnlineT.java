package model;

/*
 * 客服在线登记表
 * user	客服
 * type	客服类型
 * state	客服状态	on:在线	off:离开	wait:工作中
 */
public class OnlineT {
	private String user;
	private String type;
	private String state;
	private String serverId;
	
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
