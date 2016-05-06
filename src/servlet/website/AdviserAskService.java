package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import control.DialogInfoC;

public class AdviserAskService extends HttpServlet {

	public AdviserAskService() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init() throws ServletException {
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	/*
	 * 客服询问是否有新服务
	 * 有新的服务：answer=="1"
	 * 没有新服务：answer=="-1"; 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String answer = "-1";
		try{
			HttpSession session = request.getSession();
			String serverId = (String) session.getAttribute("serverId");
			System.out.println("serverId:"+serverId);
			//查询服务
			String[] dialog = new String[2];
			dialog = DialogInfoC.queryService(serverId);
			
			System.out.println("dialogId:"+dialog[0]+" userId:"+dialog[1]);//////////////////////////////////////////
			
			if(dialog[0]!=null){
				session.setAttribute("dialogId", dialog[0]);
				session.setAttribute("userId",dialog[1]);
				answer = "1";
			}		
		}catch(Exception e){
			
		}finally{
			writer.write(answer);
		}
	}



}
