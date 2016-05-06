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
	 * 1、将dialog表中dialogState状态由"wait"-->"off"
	 * 2、
	 */
	@SuppressWarnings({ "resource" })
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//dialog表
		HttpSession session = event.getSession();
		//mongodb 客户端
		MongoClient client = new MongoClient("localhost");
		//mongodb 数据库
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCollection<Document> dialogTable = db.getCollection("dialog");
		//server表
		MongoCollection<Document> serverTable = db.getCollection("server");
		MongoCursor<Document> cursor = null;
		try{
			//建立查询条件
			BasicDBObject queryDialog = new BasicDBObject()
				.append("dialogId", (String)session.getAttribute("dialogId"));
			//游标
			cursor = dialogTable.find(queryDialog).iterator();
			Document dialog = cursor.next();
			BasicDBObject updateDialog = new BasicDBObject()
				.append("$set", new BasicDBObject().append("dialogState", "off"));
			//将dialog表中dialogState状态由"on"-->"off"
			dialogTable.updateOne(dialog, updateDialog);
			
			//建立查询条件
			BasicDBObject queryServer = new BasicDBObject()
				.append("serverId", (String)session.getAttribute("serverId"));
			//游标
			cursor = serverTable.find(queryServer).iterator();
			Document server = cursor.next();
			BasicDBObject updateServer = new BasicDBObject()
				.append("$set", new BasicDBObject().append("state", "on"));
			//online表中state状态由wait-->on
			serverTable.updateOne(server, updateServer);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
		}
	}

}
