package servlet.website;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.DialogInfoC;
import control.MessageC;
import control.OnlineC;
import control.ServerC;

public class AdviserAskMessage extends HttpServlet {

	public AdviserAskMessage() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public void init() throws ServletException {
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	/**
	 * ���ؿͷ�δ����Ϣ
	 * @author lch
	 * ����"-1"����ǰû�пɶ���Ϣ
	 * ���� messages����ǰ��Ϣ�б�
	 * ����"-2":�Ự�ѽ���
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String messages = "-1";
		try{
			String serverId = (String)session.getAttribute("serverId");
			System.out.println("adviserAskMessage-->serverId:"+serverId);/////////////////////////
			messages = MessageC.askMessage(serverId,"0");
			if(messages=="-1"){
				//û������Ϣ���ж��Ƿ�Ӧ���Զ��Ͽ��Ự
				String dialogId = (String)session.getAttribute("dialogId");
				String state = DialogInfoC.checkDialogState(dialogId);
				if(state=="off"){
					messages = "-2";
					//�Ự�ѹرգ����ͷ�״̬�ı� wait-->on
					OnlineC.beOnline(serverId);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(messages);
		}
	}


}
