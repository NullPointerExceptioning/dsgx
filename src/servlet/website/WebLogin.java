package servlet.website;
//dsgx2
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.LoginC;
import control.ServerC;
import model.LoginT;
import model.OnlineT;

public class WebLogin extends HttpServlet {


	public WebLogin() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("login start...");
		request.setCharacterEncoding("utf-8");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		
		LoginT login = new LoginT();
		login.setUser(user);
		login.setPwd(pwd);
		int result = LoginC.checkLogin(login);
		//创立会话链接，将客服serverId注册到服务上
		if(result==1){
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60*30);
			String serverId = ServerC.queryId(user);
			if(serverId==null)
				System.out.println("get serverId happened error");
			else
				session.setAttribute("serverId", serverId);			
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.write(String.valueOf(result));	
	}
	public void init() throws ServletException {
	}
}
