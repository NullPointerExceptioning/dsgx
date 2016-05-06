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
	 * 1、用户发起请求
	 * 2、服务器查询到在线的客服
	 * 3、创立会话
	 * 4、告知用户会话id
	 * @author	lch
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//输出
		Document answer = new Document();
		//是否成功建立
		String success = "-1";
		
		try{
			String userId = request.getParameter("userId");
			
			System.out.println("userId:"+userId);////////////////////////////////////////////
			String serverId = OnlineC.matchOne();
			//有客服在线，则开创会话提供服务。
			if(serverId!=null){
				String dialogId = DialogInfoC.createDialog(serverId,userId);
				System.out.println("user create dialog,dialoId is:"+dialogId);/////////////////
				//将建立的会话信息存到输出流
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
			}//输出流
			PrintWriter writer = response.getWriter();
			writer.write(answer.toJson());
		}
	}


}
