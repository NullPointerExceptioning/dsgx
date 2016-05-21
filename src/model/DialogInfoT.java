package model;

/*
 * 客服会话记录表
 * serverId	客服id
 * userId 用户id
 * dialogId	对话id
 * dialogState	对话状态	on:会话使用中	off:会话结束	wait:等待建立连接
 * dialogTime[年，月，日]	会话创立时间
 * 
 */
public class DialogInfoT {
	private String serverId;
	private String dialogId;
	private String userId;
	private String dialogTime;
	private String dialogState;
	
	public DialogInfoT(){
		this.serverId = "";
		this.userId = "";
		this.dialogState="wait";
		this.dialogTime = "";
		this.dialogId = "";
	}
	
	public String getDialogState() {
		return dialogState;
	}

	public void setDialogState(String dialogState) {
		this.dialogState = dialogState;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getDialogId() {
		return dialogId;
	}
	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}
	public String getDialogTime() {
		return dialogTime;
	}
	public void setDialogTime(String dialogTime) {
		this.dialogTime = dialogTime;
	}
	
	

}
