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
	 * ���ݿͷ�id,�û�id��ʱ�䴴���Ự
	 * serverId �ͷ�id
	 * userId	�û�id
	 * dialogState	Ĭ��off
	 * ʱ�����ϵͳĬ�ϴ���
	 * return �Ựid
	 */
	
	public static String createDialog(String serverId,String userId){
		//�Ựid
		String dialogId = "-1";
		DialogInfoT dialog = new DialogInfoT();
		dialog.setServerId(serverId);
		dialog.setUserId(userId);
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = null;
		//�α�
		MongoCursor<Document> cursor = null;
		try{
			client = new MongoClient("localhost");
			db = client.getDatabase(Values.dbName);
			//mongodb ��
			MongoCollection<Document> dialogTable = db.getCollection("dialog");
			dialogTable.insertOne(DialogInfoC.converDoc(dialog));
			//������ѯ
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
	 * �ͷ���ѯ�Ƿ����·���
	 * return service[]
	 * service[0]��serverId
	 * service[1]��userId
	 */
	@SuppressWarnings({ "finally", "resource", "null" })
	public static String[] queryService(String serverId){
		//����ֵ
		String[] service = {null,null};
		//�α�
		MongoCursor<Document> cursor = null;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = null;
		//dialog��
		MongoCollection<Document> dialogTable = null;
		try{
			db = client.getDatabase(Values.dbName);
			dialogTable = db.getCollection("dialog");
			//������ѯ
			BasicDBObject queryDialog = new BasicDBObject()
				.append("serverId", serverId).append("dialogState", "wait");
			//�α�
			cursor = dialogTable.find(queryDialog).iterator();
			//�ж��Ƿ��п��Կ�ʼ�ĶԻ�
			if(cursor.hasNext()){
				System.out.println("dialogInfo-->dialogstate:wait!!!");////////////////////
				Document dialog = cursor.next();
				//�����·��񣬽�dialogState״̬�� wait--> on
				BasicDBObject updateDialog = new BasicDBObject()
					.append("$set", new BasicDBObject().append("dialogState", "on"));
				dialogTable.updateOne(dialog, updateDialog);
				service[0] = dialog.getString("dialogId");
				service[1] = dialog.getString("userId");
			}//�жϵ�ǰ�Ự�Ƿ��ڽ���	
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
	 * �ر�Ϊ�û��ṩ�ĻỰ
	 */
	@SuppressWarnings({ "finally", "resource" })
	public static String CloseIt(String userId){
		String result = "-1";
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//�Ự��
		MongoCollection<Document> dialogTable = db.getCollection("dialog");
		//�ͷ���
		MongoCollection<Document> onlineTable = db.getCollection("online");
		//�α�
		MongoCursor<Document> cursor = null;
		try{
			//���ỰdialogState״̬�� "on"-->"off"
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
			//��ѯ״̬
			BasicDBObject queryServer = new BasicDBObject().append("serverId", serverId);
			//��server״̬��"wait"-->"on"
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
	 * ���Ự״̬
	 */
	@SuppressWarnings("finally")
	public static String checkDialogState(String dialogId){
		String state = "off";
		MongoCursor<Document> cursor = null;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
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
	 * ��ȡ�ͷ���Ӧ��dialogInfo��Ϣ
	 */
	@SuppressWarnings("finally")
	public static String getDialogs(String serverId){
		//������
		String result = "-1";
		//�����洢�ռ�
		ArrayList<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = null;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		@SuppressWarnings("null")
		MongoDatabase db = client.getDatabase(Values.dbName);
		//�Ự��
		MongoCollection<Document> table = db.getCollection("dialog");
		try{
			//������ѯ
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
