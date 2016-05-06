package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckLogin extends HttpServlet {


	public CheckLogin() {
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String result = "-1";
		HttpSession session = request.getSession();
		String adviser = (String)session.getAttribute("serverId");
		if(adviser!=null)
			result="1";
		writer.write(result);
	}



}
