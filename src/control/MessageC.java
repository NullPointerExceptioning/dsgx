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
	 * �����û���Ϊ��ȡ����Ϣ
	 * �ɹ�return	��Ϣ�б�
	 * ʧ��return	null
	 */
	@SuppressWarnings("finally")
	public static String askMessage(String id,String messageFrom){
		//���ص���Ϣ
		String messages = "-1";
		//��Ϣ�б�
		ArrayList<MessageT> messageList = new ArrayList<MessageT>();
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//message��
		MongoCollection<Document> messageTable = db.getCollection("message");
		//�α�
		MongoCursor<Document> cursor = null;
		//google gson��
		Gson gson = new Gson();
		try{
			//������ѯ����
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
					//�޸�messageΪ�Ѷ� "0"-->"1"
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
	 * ��message�������ݿ�
	 */
	public static void inserMessage(Document message){
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
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
	 * ��messageTת��ΪDocument��ʽ 
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
