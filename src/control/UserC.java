package control;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Values;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.UserT;

public class UserC {
	/*
	 * function 注册用户
	 * 注册成功return userId
	 * 注册失败return null
	 */
	@SuppressWarnings({ "resource", "finally" })
	public static String regsitNewUser(UserT register){
		String userId = null;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//user表
		MongoCollection<Document> userTable = null;
		//查询的结果
		MongoCursor<Document> cursor = null;
		try{
			//查询user
			BasicDBObject queryExit = new BasicDBObject();
			queryExit.put("user", register.getUser());
			userTable = db.getCollection("user");
			cursor = userTable.find(queryExit).iterator();
			if(cursor.hasNext()){
				return null;
			}else{
				userTable.insertOne(UserC.convertDoc(register));
				//查询id
				BasicDBObject queryId = new BasicDBObject();
				queryId.append("user",register.getUser());
				cursor = userTable.find(queryId).iterator();
				//user信息
				Document user = cursor.next();
				userId = user.getObjectId("_id").toString();
				queryId.append("userId", userId);
				BasicDBObject updateUser = new BasicDBObject().append("$set", queryId);
				userTable.updateOne(user, updateUser);			
				//创建存储用户视频信息的文件夹
				new QcloudC().createFloder(register.getUser());
			}			
		}catch(Exception e){
			System.out.println("regist failed");
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return userId;
		}
	}
	/*
	 * 验证用户输入的账号密码
	 * 成功：返回用户个人信息
	 * 失败：返回 "-1"
	 */
	@SuppressWarnings("finally")
	public static String checkLogin(String user,String pwd){
		//登陆者
		String loginer="-1";
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//查询游标
		MongoCursor<Document> cursor = null;
		try{
			BasicDBObject queryUser = new BasicDBObject();
			queryUser.append("user", user);
			queryUser.append("pwd", pwd);
			cursor = db.getCollection("user").find(queryUser).iterator();
			//账号密码正确
			if(cursor.hasNext()){
				loginer = cursor.next().toJson();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return loginer;
		}
	}
	/*
	 * userT类转换为Document类
	 */
	private static Document convertDoc(UserT u){
		Document d= new Document().append("age", u.getAge())
				.append("email", u.getEmail()).append("local", u.getLocal())
				.append("nickName", u.getNickName()).append("phone", u.getPhone())
				.append("pwd", u.getPwd()).append("sex", u.getSex())
				.append("birthdy", u.getBirthday())
				.append("user", u.getUser()).append("userId", u.getUserId());		
		return d;
	}

}
