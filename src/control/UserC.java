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
	 * function ע���û�
	 * ע��ɹ�return userId
	 * ע��ʧ��return null
	 */
	@SuppressWarnings({ "resource", "finally" })
	public static String regsitNewUser(UserT register){
		String userId = null;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//user��
		MongoCollection<Document> userTable = null;
		//��ѯ�Ľ��
		MongoCursor<Document> cursor = null;
		try{
			//��ѯuser
			BasicDBObject queryExit = new BasicDBObject();
			queryExit.put("user", register.getUser());
			userTable = db.getCollection("user");
			cursor = userTable.find(queryExit).iterator();
			if(cursor.hasNext()){
				return null;
			}else{
				userTable.insertOne(UserC.convertDoc(register));
				//��ѯid
				BasicDBObject queryId = new BasicDBObject();
				queryId.append("user",register.getUser());
				cursor = userTable.find(queryId).iterator();
				//user��Ϣ
				Document user = cursor.next();
				userId = user.getObjectId("_id").toString();
				queryId.append("userId", userId);
				BasicDBObject updateUser = new BasicDBObject().append("$set", queryId);
				userTable.updateOne(user, updateUser);			
				//�����洢�û���Ƶ��Ϣ���ļ���
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
	 * ��֤�û�������˺�����
	 * �ɹ��������û�������Ϣ
	 * ʧ�ܣ����� "-1"
	 */
	@SuppressWarnings("finally")
	public static String checkLogin(String user,String pwd){
		//��½��
		String loginer="-1";
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//��ѯ�α�
		MongoCursor<Document> cursor = null;
		try{
			BasicDBObject queryUser = new BasicDBObject();
			queryUser.append("user", user);
			queryUser.append("pwd", pwd);
			cursor = db.getCollection("user").find(queryUser).iterator();
			//�˺�������ȷ
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
	 * userT��ת��ΪDocument��
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
