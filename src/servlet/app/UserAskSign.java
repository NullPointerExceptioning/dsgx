package servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.bson.Document;

import com.qcloud.common.Base64Util;
import com.qcloud.common.HMACSHA1;
import com.qcloud.common.Sign;

import tools.Values;

public class UserAskSign extends HttpServlet {

	public UserAskSign() {
		super();
	}
	public void destroy() {
		super.destroy();
	}
	public void init() throws ServletException {
	}
	/**
	 * �����ʵ��û�����ǩ��
	 * @author lch
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//�����
		PrintWriter writer = response.getWriter();
		//������
		String result = null;
		try{
			String type = request.getParameter("type");
			result = UserAskSign.getSign(type);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.write(result);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
	
	/*
	 * ��ȡǩ��
	 */
	@SuppressWarnings("finally")
	public static String getSign(String type){
		type = Values.videoBucketName;
		System.out.println("is it ok?type:"+type);///////////////
		long expired = System.currentTimeMillis() / 1000 + 60;
		//ǩ��
		//����ֵ
		Document doc = new Document();
		try {
			String sign = Sign.appSign(Values.AppId, Values.secretId,
					Values.secretKey, expired, type);
			doc.append("sign", sign);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return doc.toJson();
		}	
	}


}
