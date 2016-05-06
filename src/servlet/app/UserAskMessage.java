package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.google.gson.Gson;

import control.MessageC;

public class UserAskMessage extends HttpServlet {

	public UserAskMessage() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init() throws ServletException {
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	/**
	 * 返回属于用户的信息
	 * 没有信息返回"-1"
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//输出流
		PrintWriter writer = response.getWriter();
		String messages = "-1";	
		try{
			//用户请求的参数
			String userId = request.getParameter("userId");
			messages = MessageC.askMessage(userId,"1");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(messages);
		}		
	}


}
