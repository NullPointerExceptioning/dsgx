package model;

/*
 * �ͷ����ߵǼǱ�
 * user	�ͷ�
 * type	�ͷ�����
 * state	�ͷ�״̬	on:����	off:�뿪	wait:������
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
