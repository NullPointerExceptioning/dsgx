package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import control.UserC;

public class UserLogin extends HttpServlet {

	public UserLogin() {
		super();
	}
	public void init() throws ServletException {
	}
	public void destroy() {
		super.destroy();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	/**
	 * 为新用户注册
	 * 成功return	"1"
	 * 失败return	"-1"
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//输出的结果
		String result = "-1";
		try{
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			result = UserC.checkLogin(user,pwd);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//输出流
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}


}
