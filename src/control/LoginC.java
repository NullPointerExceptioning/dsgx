package control;

import org.bson.Document;

import tools.Values;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.LoginT;
import model.ServerT;

public class LoginC {
	
	/*
	 * 将login表转化为Document
	 */
	public static Document convertDoc(LoginT login){
		Document doc = new Document()
			.append("user", login.getUser())
			.append("pwd",login.getPwd())
			.append("type", login.getType());
		return doc;
	}
	
	/*
	 * 判断登录结果
	 * 0	失败
	 * 1	成功，登录者是客服
	 * 2	成功，登录者是经理
	 */
	@SuppressWarnings("finally")
	public static int checkLogin(LoginT login){
		System.out.println("checkLogin...");///////////
		//返回的类型值
		int type = 0;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("user", login.getUser());
			query.put("pwd", login.getPwd());
			cursor = db.getCollection("login")
					.find(query).iterator();
			if(cursor.hasNext()){
				Document doc = cursor.next();
				type = Integer.parseInt((String) doc.get("type"));
				/*
				 * 将客服状态改为online
				 * 有机会把getResult 修改一个变量名
				 */
				if(type==1){
					OnlineC.beOnline((String)doc.getString("user"));
				}
			}
			System.out.println("type:"+type);///////////////
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return type;
		}

	}
	/*
	 * 成功注册新客服之后将客服信息登录login表
	 * register	客服信息
	 * return 1	成功
	 */
	@SuppressWarnings("finally")
	public static int addLoginInfo(ServerT register){
		//结果
		int result = 0;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		try{
			MongoCollection<Document> table = db.getCollection("login");
			LoginT login = new LoginT();
			login.setUser(register.getUser());
			login.setPwd(register.getPwd());
			table.insertOne(LoginC.convertDoc(login));		
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.close();
			return result;
		}


	}
	

}
