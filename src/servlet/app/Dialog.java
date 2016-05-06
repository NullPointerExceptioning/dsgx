package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import control.DialogInfoC;
import control.OnlineC;

public class Dialog extends HttpServlet {
	public Dialog() {
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
	 * 1���û���������
	 * 2����������ѯ�����ߵĿͷ�
	 * 3�������Ự
	 * 4����֪�û��Ựid
	 * @author	lch
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//���
		Document answer = new Document();
		//�Ƿ�ɹ�����
		String success = "-1";
		
		try{
			String userId = request.getParameter("userId");
			
			System.out.println("userId:"+userId);////////////////////////////////////////////
			String serverId = OnlineC.matchOne();
			//�пͷ����ߣ��򿪴��Ự�ṩ����
			if(serverId!=null){
				String dialogId = DialogInfoC.createDialog(serverId,userId);
				System.out.println("user create dialog,dialoId is:"+dialogId);/////////////////
				//�������ĻỰ��Ϣ�浽�����
				answer.append("dialogId", dialogId).append("serverId", serverId);
				success="1";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try {
				answer.append("success", success);
			} catch (Exception e) {
				e.printStackTrace();
			}//�����
			PrintWriter writer = response.getWriter();
			writer.write(answer.toJson());
		}
	}


}
