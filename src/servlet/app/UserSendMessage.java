package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import control.DialogInfoC;
import control.MessageC;
import model.MessageT;

public class UserSendMessage extends HttpServlet {
	public UserSendMessage() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init() throws ServletException {
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	/**
	 * 接受客户端发送的信息
	 * 成功存储数据返回	"1"
	 * 出现异常情况返回	"-1"
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("user send message..");////////////////////////////////
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String answer = "-1";
		try{
			//检查会话是否已经结束
			System.out.println("user send message dialogId:"+request.getParameter("dialogId"));
			String dialogState = DialogInfoC.checkDialogState(request.getParameter("dialogId"));/////////
			System.out.println("dialogState:"+dialogState);
			System.out.println(request.getParameter("message"));////////////////////////////////////////
			if(dialogState!="off"){
				MessageT m = UserSendMessage.parseMessage(request);
				Document message = MessageC.convertDoc(m);
				MessageC.inserMessage(message);
				answer="1";	
			}	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(answer);
		}
	}
	
	
	/*
	 * 解析收到的message
	 * 
	 */
	public static MessageT parseMessage(HttpServletRequest request){
		System.out.println("are you kidding me?"+request.getParameter("serverId"));
		MessageT m = new MessageT();
		//add these attributes
		m.setUserId(request.getParameter("userId"));
		m.setServerId(request.getParameter("serverId"));
		m.setDialogId(request.getParameter("dialogId"));
		//获取客服端发送的参数
		System.out.println("message:"+request.getParameter("message"));////////////////////////////////
		m.setMessage(request.getParameter("message"));
		m.setMessageTime(request.getParameter("messageTime"));
		m.setMessageType(request.getParameter("messageType"));
		m.setMessageUrl(request.getParameter("messageUrl"));
		m.setMessageFrom("0");
		m.setMessageState("0");
	return m;
	}
	
}
