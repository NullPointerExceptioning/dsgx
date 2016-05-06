package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserT;
import control.UserC;


public class UserRegist extends HttpServlet {
	public UserRegist() {
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
	 * Ϊ�û�ע��
	 * �ɹ�return	userId
	 * ʧ��return	"-1"
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		request.setCharacterEncoding("utf-8");
		//�û�userId
		String userId = "-1";
		try{
			UserT register = UserRegist.parseRequest(request);
			userId = UserC.regsitNewUser(register);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setCharacterEncoding("utf-8");
			//�����
			PrintWriter writer = response.getWriter();
			writer.write(userId);	
		}
	}
	/*
	 * ��ȡ�û����͵ĸ���ע����Ϣ��ת��Ϊuser��
	 */
	private static UserT parseRequest(HttpServletRequest request){
		//ע����
		UserT register = new UserT();
		register.setUser(request.getParameter("user"));
		register.setPwd(request.getParameter("password"));
		register.setNickName((request.getParameter("nickname")));
		register.setPhone(request.getParameter("mobile"));
		return register;
	}
}
