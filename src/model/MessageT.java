package model;

/*
 * �Ự��
 * dialogId	�Ựid
 * message	��Ϣ����
 * serverId	��Ϣ���շ�
 * userId	��Ϣ���ͷ�
 * messageFrom	��Ϣ���ͷ�		1��server 0:user
 * messageType	��Ϣ����	text �ı�	video ��Ƶ	image ͼƬ
 * messageUrl	��Ϣ�����ַ
 * messageTime	��Ϣ����ʱ��
 * messageState	��Ϣ��ǰ״̬	1���Ѷ�	0��δ��
 */
public class MessageT {
	private String message;
	private String serverId;
	private String userId;
	private String messageFrom;
	private String messageType;
	private String messageUrl;
	private String messageTime;
	private String messageState;
	private String dialogId;
	
	/*
	 * ȫ����ʼ����
	 */
	public MessageT(){
		this.message = "";
		this.serverId = "";
		this.userId = "";
		this.messageFrom = "";
		this.messageType = "";
		this.messageUrl = "";
		this.messageTime = "";
		this.messageState = "";
		this.dialogId = "";
	}
	
	public String getDialogId() {
		return dialogId;
	}
	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageFrom() {
		return messageFrom;
	}
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageUrl() {
		return messageUrl;
	}
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}
	public String getMessageState() {
		return messageState;
	}
	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}
	
	
	
	

}
