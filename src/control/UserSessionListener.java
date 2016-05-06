package control;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.bson.Document;

import tools.Values;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UserSessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("new session:"+event.getSession().getId());
	}
	/*
	 * 1����dialog����dialogState״̬��"wait"-->"off"
	 * 2��
	 */
	@SuppressWarnings({ "resource" })
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//dialog��
		HttpSession session = event.getSession();
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCollection<Document> dialogTable = db.getCollection("dialog");
		//server��
		MongoCollection<Document> serverTable = db.getCollection("server");
		MongoCursor<Document> cursor = null;
		try{
			//������ѯ����
			BasicDBObject queryDialog = new BasicDBObject()
				.append("dialogId", (String)session.getAttribute("dialogId"));
			//�α�
			cursor = dialogTable.find(queryDialog).iterator();
			Document dialog = cursor.next();
			BasicDBObject updateDialog = new BasicDBObject()
				.append("$set", new BasicDBObject().append("dialogState", "off"));
			//��dialog����dialogState״̬��"on"-->"off"
			dialogTable.updateOne(dialog, updateDialog);
			
			//������ѯ����
			BasicDBObject queryServer = new BasicDBObject()
				.append("serverId", (String)session.getAttribute("serverId"));
			//�α�
			cursor = serverTable.find(queryServer).iterator();
			Document server = cursor.next();
			BasicDBObject updateServer = new BasicDBObject()
				.append("$set", new BasicDBObject().append("state", "on"));
			//online����state״̬��wait-->on
			serverTable.updateOne(server, updateServer);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
		}
	}

}
