package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import control.DialogInfoC;

public class CloseDialog extends HttpServlet {


	public CloseDialog() {
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
	
	/**
	 * �û������Ự
	 * �ɹ�return	"1"
	 * ʧ��return 	"-1"
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("close.......dialog........");//////////////
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//���
		Document answer = new Document();
		//ֵ
		String value = "-1";
		try{
			String userId = request.getParameter("userId");
			//���Ự״̬�ر�
			DialogInfoC.CloseIt(userId);
			value = "1";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			answer.append("success", value);
			writer.write(answer.toJson());
		}
	}


}
