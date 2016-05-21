package control;

import java.util.ArrayList;

import model.ServerT;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Values;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class OnlineC {
	/*
	 * 获得所有在线的客服信息
	 * 成功return:客服们的信息
	 * 失败return:null
	 */
	@SuppressWarnings("finally")
	public static JSONArray getOnlineServers(){
		//查询在线客服
		BasicDBObject queryOnline = new BasicDBObject();
		queryOnline.put("state", "on");
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//online表
		MongoCollection<Document> table = db.getCollection("online");
		//查询的游标
		MongoCursor<Document> cursor = null;
		//返回的客服信息
		JSONArray servers = null;
		try{
			cursor = table.find(queryOnline).iterator();
			//临时存储数列
			ArrayList<JSONObject> temporaryList = new ArrayList<JSONObject>();
			while(cursor.hasNext()){		
					JSONObject obj;
					obj = new JSONObject(cursor.next().toJson());
					temporaryList.add(obj);
			}
			servers = new JSONArray(temporaryList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return servers;
		}

	}
	/*
	 * 获得所有工作的客服信息
	 * 成功return:客服们的信息
	 * 失败return:null
	 */
	@SuppressWarnings({ "finally", "resource" })
	public static JSONArray getServers(){
		//查询在线客服
		BasicDBObject queryOn = new BasicDBObject();
		BasicDBObject queryBusy = new BasicDBObject();
		queryOn.put("state", "on");
		queryBusy.put("state","wait");
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//online表
		MongoCollection<Document> table = db.getCollection("online");
		//查询的游标
		MongoCursor<Document> cursor = null;
		//返回的客服信息
		JSONArray servers = null;
		try{
			cursor = table.find(queryOn).iterator();
			//临时存储数列
			ArrayList<JSONObject> temporaryList = new ArrayList<JSONObject>();
			while(cursor.hasNext()){		
					JSONObject obj;
					obj = new JSONObject(cursor.next().toJson());
					temporaryList.add(obj);
			}
			cursor = null;
			cursor = table.find(queryBusy).iterator();
			while(cursor.hasNext()){
				JSONObject obj;
				obj = new JSONObject(cursor.next().toJson());
				temporaryList.add(obj);
			}
			servers = new JSONArray(temporaryList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return servers;
		}

	}
	
	/*
	 * 找寻一名在线的空闲客服。
	 * 成功return:客服id
	 * 失败return:null
	 */
	@SuppressWarnings("finally")
	public static String matchOne(){
		//返回的信息
		String serverId = null;
		JSONArray servers = new JSONArray();
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try {
			servers = OnlineC.getOnlineServers();
			if(servers!=null){
				int random = (int) Math.floor((Math.random()*servers.length())) ;
				//选中的客服
				serverId = servers.getJSONObject(random).getString("serverId");
				//online表
				MongoCollection<Document> table = db.getCollection("online");
				//客服在线状态改为wait
				cursor = table.find(new BasicDBObject().
						append("serverId", serverId)).iterator();
				Document server = cursor.next();
				BasicDBObject updateInfo = new BasicDBObject().
						append("$set", new BasicDBObject().append("state", "wait"));
				table.updateOne(server, updateInfo);
			}else
				System.out.println("none server is on..");///////////////////////////////
			
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return serverId;
		}
	}
	/*
	 * 将客服状态改为在线
	 */
	@SuppressWarnings("finally")
	public static String beOnline(String user){
		String result = "-1";
		BasicDBObject query = new BasicDBObject();
		query.append("user", user);		
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try{
			MongoCollection<Document> onlineTable = db.getCollection("online");
			cursor = onlineTable.find(query).iterator();
			if(cursor.hasNext()){
				//更新状态
				Document doc = cursor.next();
				BasicDBObject obj = new BasicDBObject();
				obj.append("state","on");
				BasicDBObject newDoc = new BasicDBObject().append("$set",obj);
				onlineTable.updateOne(doc, newDoc);
			}
			result = "success";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return result;
		}

		
	}
	/*
	 * 客服在线登记表
	 * serverId	客服id
	 * type	客服类型
	 * state	客服状态
	 */
	@SuppressWarnings("finally")
	public int addOnlineInfo(ServerT register){
		int result = 0;
		String user = register.getUser();
		String type = register.getRank();
		String serverId = register.getServerId();
		String state = "off";
		
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);	
		try{
			MongoCollection<Document> table = db.getCollection("online");
			table.insertOne(new Document().append("user",user).append("type",type)
				.append("state", state).append("serverId", serverId));
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.close();
			return result;
		}
	}

}
