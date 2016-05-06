package control;

import java.util.ArrayList;
import java.util.List;

import model.MessageT;

import org.bson.Document;
import org.json.JSONObject;

import tools.Values;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MessageC {
	/*
	 * 返回用户还为读取的信息
	 * 成功return	消息列表
	 * 失败return	null
	 */
	@SuppressWarnings("finally")
	public static String askMessage(String id,String messageFrom){
		//返回的消息
		String messages = "-1";
		//消息列表
		ArrayList<MessageT> messageList = new ArrayList<MessageT>();
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		//message表
		MongoCollection<Document> messageTable = db.getCollection("message");
		//游标
		MongoCursor<Document> cursor = null;
		//google gson库
		Gson gson = new Gson();
		try{
			//建立查询条件
			BasicDBObject queryMessages = new BasicDBObject()
				.append("messageFrom", messageFrom).append("messageState","0");
			if(messageFrom=="0"){
				queryMessages.append("serverId", id);
			}else if(messageFrom=="1"){
				queryMessages.append("userId",id);
			}
			cursor = messageTable.find(queryMessages).iterator();
			if(cursor.hasNext()){
				for(int i=0;i<3;i++)
					System.out.println("");///////////////////////////////////////////
				System.out.println("you have message!!!!!!!!!!!!!");/////////////////
				while(cursor.hasNext()){
					Document messageDoc = cursor.next();
					System.out.println(messageDoc.toJson());/////////////////////////////////////////
					MessageT message = gson.fromJson(messageDoc.toJson(),MessageT.class);
					messageList.add(message);
					//修改message为已读 "0"-->"1"
					BasicDBObject updateMessage = new BasicDBObject("$set",
							new BasicDBObject().append("messageState", "1"));
					messageTable.updateOne(messageDoc, updateMessage);
				}
				messages = gson.toJson(messageList);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return messages;
		}
	}
	
	
	
	/*
	 * 将message插入数据库
	 */
	public static void inserMessage(Document message){
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		try{
			MongoCollection<Document> table = db.getCollection("message");
			table.insertOne(message);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.close();
		}
	}
	/*
	 * 将messageT转化为Document格式 
	 */
	public static Document convertDoc(MessageT message){
		Document d = new Document()
		.append("dialogId",	message.getDialogId())
		.append("message", message.getMessage()).append("messageFrom", message.getMessageFrom())
		.append("messageState", message.getMessageState()).append("messageTime", message.getMessageTime())
		.append("messageType", message.getMessageType()).append("messageUrl", message.getMessageUrl())
		.append("serverId", message.getServerId()).append("userId", message.getUserId());
		return d;
	}

}
