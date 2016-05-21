package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import control.DialogInfoC;

public class GetServerLog extends HttpServlet {

	public GetServerLog() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//输出流
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		String dialogList = "-1";
		try{
			//客服serverId
			String serverId = (String)session.getAttribute("serverId");
			//客服所有对话列表
			dialogList = DialogInfoC.getDialogs(serverId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(dialogList);
		}
	}

}
