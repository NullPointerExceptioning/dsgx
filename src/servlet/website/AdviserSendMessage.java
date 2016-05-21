package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import tools.Tool;
import model.MessageT;
import control.DialogInfoC;
import control.MessageC;

public class AdviserSendMessage extends HttpServlet {

	public AdviserSendMessage() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init() throws ServletException {
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	/**
	 * �洢�ͷ����͵���Ϣ
	 * @author lch
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");		
		//�����
		PrintWriter writer = response.getWriter();
		String answer = "-1";
		try{
			String dialogId = (String)session.getAttribute("dialogId");
			String dialogState = DialogInfoC.checkDialogState(dialogId);
			
			//�Ự�ѹر�
			if(dialogState=="off"){
				
			}
			//�Ự����ʹ����
			else if(dialogState!="off"){
				//��Ϣ
				MessageT message = AdviserSendMessage.parseMessage(request,session);
				//ת��ʽ
				Document docMessage = MessageC.convertDoc(message);
				//�����ݿ�
				MessageC.inserMessage(docMessage);
				answer = "1";
			}

			System.out.println("state:"+dialogState);/////////////////////////////����ɾ��
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(answer);
		}



	/*
	 * �����û����͵����ݰ�	
	 */
	}
	private static MessageT parseMessage(HttpServletRequest request,HttpSession session){
		MessageT message = new MessageT();
		//��ȡsession�б��������
		message.setDialogId((String)session.getAttribute("dialogId"));
		message.setServerId((String)session.getAttribute("serverId"));
		message.setUserId((String)session.getAttribute("userId"));
		//��ϵͳ��ȡʱ��
		message.setMessageTime(String.valueOf(System.currentTimeMillis()));
		//��ȡ�ͻ��˷��͵�����
		String text = request.getParameter("message");
		String type = request.getParameter("messageType");
		String url = request.getParameter("messageUrl");
		//��������
		message.setMessage(text);
		if(text==null){
			message.setMessageType(type);
			message.setMessageUrl(url);
		}
		else{
			type="TEXT";
			message.setMessageType(type);
			message.setMessageUrl(url);
		}
		//������Ϣ���Կͷ�����Ϣ״̬δ����
		message.setMessageFrom("1");
		message.setMessageState("0");
		return message;
	}

}
