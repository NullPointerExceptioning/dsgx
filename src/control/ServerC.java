package control;

import java.util.ArrayList;

import model.ServerT;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import tools.Values;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ServerC {
	/*
	 * 注册新客服
	 * return 1	成功
	 * return 0	失败
	 */
	public  int regist(JSONObject obj){	
		try {
			ServerT register = new ServerT();
			register.setUser(obj.getString("user"));
			register.setPwd(obj.getString("pwd"));
			register.setSex(obj.getString("sex"));
			register.setEmail(obj.getString("email"));
			register.setNickName(obj.getString("name"));
			register.setRank(obj.getString("type"));
			register.setAge(obj.getString("age"));
			
			int result = new ServerC().addServer(register);
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	/*
	 * 把server表转化为server document
	 */
	public static Document convertDoc(ServerT server){
		Document doc = new Document();
		doc.append("age", server.getAge())
		.append("email",server.getEmail()).append("nickName",server.getNickName())
		.append("pwd", server.getPwd()).append("rank", server.getRank())
		.append("serverId",server.getServerId()).append("sex", server.getSex())
		.append("user", server.getUser());	
		return doc;
	}
	/*
	 * 将客服信息加入数据库
	 * return 1	注册客服成功
	 * return 0   客服账号同名，注册失败
	 */
	@SuppressWarnings({ "finally", "resource" })
	private int addServer(ServerT register){
		int result = 0;
		BasicDBObject query = new BasicDBObject();
		query.put("user", register.getUser());
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);	
		MongoCollection<Document> table = db.getCollection("server");
		MongoCursor<Document> cursor = null;
		try{
			cursor = table.find(query).iterator();
			if(cursor.hasNext()){
				return 0;
			}else{
				Document doc = ServerC.convertDoc(register);
				table.insertOne(doc);
				cursor = table.find(query).iterator();
				String _id = cursor.next().getObjectId("_id").toString();
				BasicDBObject newDoc = new BasicDBObject().append("$set",new BasicDBObject().append("serverId", _id));
				table.updateOne(doc, newDoc);
				/*
				 * 接着在login表中添加客服信息
				 */
				register.setServerId(_id);
				int addResult = LoginC.addLoginInfo(register);
				if(addResult==1){
					/*
					 * 添加成功返回	1
					 * 失败返回	0
					 */
					result = new OnlineC().addOnlineInfo(register);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return result;
		}
		
	}
	/*
	 * 获取客服的信息
	 */
	@SuppressWarnings("finally")
	public static ArrayList<JSONObject> getServers(){
		//返回值
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);	
		MongoCursor<Document> cursor = null;
		try{
			MongoCollection<Document> table = db.getCollection("server");
			cursor = table.find().iterator();
			while(cursor.hasNext()){
					JSONTokener jsonT = new JSONTokener(cursor.next().toJson());
					JSONObject obj = (JSONObject)jsonT.nextValue();
					list.add(obj);
			}			
		}catch(Exception e){
			list = null;
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return list;
		}
	}

	@SuppressWarnings("finally")
	public static String queryId(String user){
		//返回值
		String result = null;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);	
		BasicDBObject query = new BasicDBObject();
		query.append("user", user);
		MongoCursor<Document> cursor = null;
		try{
			cursor = db.getCollection("server").find(query).iterator();
			if(cursor.hasNext()){
				String id = cursor.next().getString("serverId");
				result = id;
			}
		}catch(Exception e){
			
		}finally{
			cursor.close();
			client.close();
			return result;
		}
	}

}
