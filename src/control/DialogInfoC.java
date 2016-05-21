package control;

import java.util.ArrayList;

import org.bson.Document;

import tools.Values;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.DialogInfoT;

public class DialogInfoC {
	/*
	 * 根据客服id,用户id，时间创立会话
	 * serverId 客服id
	 * userId	用户id
	 * dialogState	默认off
	 * 时间会由系统默认创立
	 * return 会话id
	 */
	
	public static String createDialog(String serverId,String userId){
		//会话id
		String dialogId = "-1";
		DialogInfoT dialog = new DialogInfoT();
		dialog.setServerId(serverId);
		dialog.setUserId(userId);
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = null;
		//游标
		MongoCursor<Document> cursor = null;
		try{
			client = new MongoClient("localhost");
			db = client.getDatabase(Values.dbName);
			//mongodb 表
			MongoCollection<Document> dialogTable = db.getCollection("dialog");
			dialogTable.insertOne(DialogInfoC.converDoc(dialog));
			//建立查询
			BasicDBObject queryId = new BasicDBObject();
			queryId.append("serverId", serverId).append("dialogState", "wait");
			cursor = dialogTable.find(queryId).iterator();
			Document doc = cursor.next();
			dialogId = doc.getObjectId("_id").toString();
			BasicDBObject newDoc = new BasicDBObject()
				.append("$set", new BasicDBObject().append("dialogId", dialogId));
			dialogTable.updateOne(doc, newDoc);
		}catch(Exception e){
			
		}finally{
			cursor.close();
			client.close();
		}
		return dialogId;
	}
	
	public static Document converDoc(DialogInfoT dialog){
		Document d = new Document().append("serverId", dialog.getServerId())
				.append("userId", dialog.getUserId())
				.append("dialogTime", dialog.getDialogTime())
				.append("dialogId", dialog.getDialogId())
				.append("dialogState", dialog.getDialogState());
		System.out.println(d.toJson());
		return d;
	}

	/*
	 * 客服查询是否有新服务
	 * return service[]
	 * service[0]是serverId
	 * service[1]是userId
	 */
	@SuppressWarnings({ "finally", "resource", "null" })
	public static String[] queryService(String serverId){
		//返回值
		String[] service = {null,null};
		//游标
		MongoCursor<Document> cursor = null;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = null;
		//dialog表
		MongoCollection<Document> dialogTable = null;
		try{
			db = client.getDatabase(Values.dbName);
			dialogTable = db.getCollection("dialog");
			//建立查询
			BasicDBObject queryDialog = new BasicDBObject()
				.append("serverId", serverId).append("dialogState", "wait");
			//游标
			cursor = dialogTable.find(queryDialog).iterator();
			//判断是否有可以开始的对话
			if(cursor.hasNext()){
				System.out.println("dialogInfo-->dialogstate:wait!!!");////////////////////
				Document dialog = cursor.next();
				//接受新服务，将dialogState状态由 wait--> on
				BasicDBObject updateDialog = new BasicDBObject()
					.append("$set", new BasicDBObject().append("dialogState", "on"));
				dialogTable.updateOne(dialog, updateDialog);
				service[0] = dialog.getString("dialogId");
				service[1] = dialog.getString("userId");
			}//判断当前会话是否还在进行	
			else{
				System.out.println("dialogInfo-->dialogstate:on!!!");////////////////////
				BasicDBObject queryOn = new BasicDBObject()
				.append("serverId", serverId).append("dialogState", "on");
				cursor = dialogTable.find(queryOn).iterator();
				if(cursor.hasNext()){
					Document dialog = cursor.next();
					service[0] = dialog.getString("dialogId");
					service[1] = dialog.getString("userId");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			cursor.close();
			client.close();
			return service;
		}
	}
	
	/*
	 * 关闭为用户提供的会话
	 */
	@SuppressWarnings({ "finally", "resource" })
	public static String CloseIt(String userId){
		String result = "-1";
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//会话表
		MongoCollection<Document> dialogTable = db.getCollection("dialog");
		//客服表
		MongoCollection<Document> onlineTable = db.getCollection("online");
		//游标
		MongoCursor<Document> cursor = null;
		try{
			//将会话dialogState状态由 "on"-->"off"
			BasicDBObject queryState = new BasicDBObject().append("userId", userId)
					.append("dialogState", "on");
			cursor = dialogTable.find(queryState).iterator();
			String serverId = "";
			if(cursor.hasNext()){
				Document docDialog = cursor.next();
				serverId = docDialog.getString("serverId");
				BasicDBObject updateDialog = new BasicDBObject("$set",
						new BasicDBObject().append("dialogState", "off"));
				dialogTable.updateOne(docDialog, updateDialog);
			}
			//查询状态
			BasicDBObject queryServer = new BasicDBObject().append("serverId", serverId);
			//将server状态由"wait"-->"on"
			cursor = null;
			cursor = onlineTable.find(queryServer).iterator();
			if(cursor.hasNext()){
				Document docOnline = cursor.next();
				BasicDBObject updateOnline = new BasicDBObject("$set",new BasicDBObject()
						.append("state", "on"));
				onlineTable.updateOne(docOnline, updateOnline);
			}			
			result = "1";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return result;
		}
	}
	/*
	 * 检查会话状态
	 */
	@SuppressWarnings("finally")
	public static String checkDialogState(String dialogId){
		String state = "off";
		MongoCursor<Document> cursor = null;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		try{
			BasicDBObject queryState = new BasicDBObject().append("dialogId", dialogId);
			cursor = db.getCollection("dialog").find(queryState).iterator();
			if(cursor.hasNext()){
				state = cursor.next().getString("dialogState");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return state;
		}	
	}
	/*
	 * 获取客服对应的dialogInfo信息
	 */
	@SuppressWarnings("finally")
	public static String getDialogs(String serverId){
		//输出结果
		String result = "-1";
		//辅助存储空间
		ArrayList<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = null;
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		@SuppressWarnings("null")
		MongoDatabase db = client.getDatabase(Values.dbName);
		//会话表
		MongoCollection<Document> table = db.getCollection("dialog");
		try{
			//建立查询
			BasicDBObject querydialog = new BasicDBObject().append("serverId", serverId);
			cursor = table.find(querydialog).iterator();
			while(cursor.hasNext()){
				Document dialog = cursor.next();
				Document doc = new Document();
				doc.append("dialogId", dialog.getString("dialogId"))
					.append("dialogTime", dialog.getString("dialogTime"))
					.append("userId", dialog.getString("userID"));
				list.add(doc);
			}
			Gson gson = new Gson();
			result = gson.toJson(list);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return result;
		}
		

	}
}
