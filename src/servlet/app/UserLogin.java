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
	 * Ϊ���û�ע��
	 * �ɹ�return	"1"
	 * ʧ��return	"-1"
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//����Ľ��
		String result = "-1";
		try{
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			result = UserC.checkLogin(user,pwd);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//�����
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}


}
