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
	 * ����������ߵĿͷ���Ϣ
	 * �ɹ�return:�ͷ��ǵ���Ϣ
	 * ʧ��return:null
	 */
	@SuppressWarnings("finally")
	public static JSONArray getOnlineServers(){
		//��ѯ���߿ͷ�
		BasicDBObject queryOnline = new BasicDBObject();
		queryOnline.put("state", "on");
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//online��
		MongoCollection<Document> table = db.getCollection("online");
		//��ѯ���α�
		MongoCursor<Document> cursor = null;
		//���صĿͷ���Ϣ
		JSONArray servers = null;
		try{
			cursor = table.find(queryOnline).iterator();
			//��ʱ�洢����
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
	 * ������й����Ŀͷ���Ϣ
	 * �ɹ�return:�ͷ��ǵ���Ϣ
	 * ʧ��return:null
	 */
	@SuppressWarnings({ "finally", "resource" })
	public static JSONArray getServers(){
		//��ѯ���߿ͷ�
		BasicDBObject queryOn = new BasicDBObject();
		BasicDBObject queryBusy = new BasicDBObject();
		queryOn.put("state", "on");
		queryBusy.put("state","wait");
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		//online��
		MongoCollection<Document> table = db.getCollection("online");
		//��ѯ���α�
		MongoCursor<Document> cursor = null;
		//���صĿͷ���Ϣ
		JSONArray servers = null;
		try{
			cursor = table.find(queryOn).iterator();
			//��ʱ�洢����
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
	 * ��Ѱһ�����ߵĿ��пͷ���
	 * �ɹ�return:�ͷ�id
	 * ʧ��return:null
	 */
	@SuppressWarnings("finally")
	public static String matchOne(){
		//���ص���Ϣ
		String serverId = null;
		JSONArray servers = new JSONArray();
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try {
			servers = OnlineC.getOnlineServers();
			if(servers!=null){
				int random = (int) Math.floor((Math.random()*servers.length())) ;
				//ѡ�еĿͷ�
				serverId = servers.getJSONObject(random).getString("serverId");
				//online��
				MongoCollection<Document> table = db.getCollection("online");
				//�ͷ�����״̬��Ϊwait
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
	 * ���ͷ�״̬��Ϊ����
	 */
	@SuppressWarnings("finally")
	public static String beOnline(String user){
		String result = "-1";
		BasicDBObject query = new BasicDBObject();
		query.append("user", user);		
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try{
			MongoCollection<Document> onlineTable = db.getCollection("online");
			cursor = onlineTable.find(query).iterator();
			if(cursor.hasNext()){
				//����״̬
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
	 * �ͷ����ߵǼǱ�
	 * serverId	�ͷ�id
	 * type	�ͷ�����
	 * state	�ͷ�״̬
	 */
	@SuppressWarnings("finally")
	public int addOnlineInfo(ServerT register){
		int result = 0;
		String user = register.getUser();
		String type = register.getRank();
		String serverId = register.getServerId();
		String state = "off";
		
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
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
