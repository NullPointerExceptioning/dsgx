package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import control.ServerC;

public class WebRegist extends HttpServlet {


	public WebRegist() {
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
		try {	
			//
			request.setCharacterEncoding("utf-8");
			String register = request.getParameter("register");
			JSONTokener jsont = new JSONTokener(register);
			JSONObject obj = (JSONObject) jsont.nextValue();
			
			int result = new ServerC().regist(obj);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			if(result==1){
				/*
				 * ע��ɹ��Ժ󽫿ͷ���Ϣ��¼online��
				 */
				writer.write("success");
			}else{
				/*
				 * 
				 * 
				 * ע��ʧ��ʱ��Ӧ��ɾ�����ݿ����й�server��login�����Ϣ
				 * 
				 * 
				 * 
				 * 
				 * 
				 */
				writer.write("failed");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
