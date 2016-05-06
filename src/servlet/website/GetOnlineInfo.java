package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import control.OnlineC;

public class GetOnlineInfo extends HttpServlet {
	public GetOnlineInfo() {
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
		JSONArray jArray =  OnlineC.getOnlineServers();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

	}


}
