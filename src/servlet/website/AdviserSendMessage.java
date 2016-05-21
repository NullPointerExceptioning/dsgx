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
	 * 存储客服发送的信息
	 * @author lch
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");		
		//输出流
		PrintWriter writer = response.getWriter();
		String answer = "-1";
		try{
			String dialogId = (String)session.getAttribute("dialogId");
			String dialogState = DialogInfoC.checkDialogState(dialogId);
			
			//会话已关闭
			if(dialogState=="off"){
				
			}
			//会话仍在使用中
			else if(dialogState!="off"){
				//消息
				MessageT message = AdviserSendMessage.parseMessage(request,session);
				//转格式
				Document docMessage = MessageC.convertDoc(message);
				//存数据库
				MessageC.inserMessage(docMessage);
				answer = "1";
			}

			System.out.println("state:"+dialogState);/////////////////////////////后期删除
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(answer);
		}



	/*
	 * 解析用户发送的数据包	
	 */
	}
	private static MessageT parseMessage(HttpServletRequest request,HttpSession session){
		MessageT message = new MessageT();
		//获取session中保存的数据
		message.setDialogId((String)session.getAttribute("dialogId"));
		message.setServerId((String)session.getAttribute("serverId"));
		message.setUserId((String)session.getAttribute("userId"));
		//从系统获取时间
		message.setMessageTime(String.valueOf(System.currentTimeMillis()));
		//获取客户端发送的内容
		String text = request.getParameter("message");
		String type = request.getParameter("messageType");
		String url = request.getParameter("messageUrl");
		//处理数据
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
		//设置信息来自客服，信息状态未读。
		message.setMessageFrom("1");
		message.setMessageState("0");
		return message;
	}

}
