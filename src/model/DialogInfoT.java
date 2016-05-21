package model;

/*
 * �ͷ��Ự��¼��
 * serverId	�ͷ�id
 * userId �û�id
 * dialogId	�Ի�id
 * dialogState	�Ի�״̬	on:�Ựʹ����	off:�Ự����	wait:�ȴ���������
 * dialogTime[�꣬�£���]	�Ự����ʱ��
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
