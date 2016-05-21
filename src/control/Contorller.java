package control;

import org.bson.Document;

import tools.Values;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.qcloud.api.VideoCloud;

public class Contorller {

	public static void main(String[] args) {
		MongoClient client = new MongoClient("localhost");
		MongoDatabase db = client.getDatabase("dsgx2");
//		db.getCollection("user").drop();
		db.getCollection("server").drop();
		db.getCollection("online").drop();
		db.getCollection("message").drop();
		db.getCollection("login").drop();
		db.getCollection("test").drop();
		db.getCollection("dialog").drop();
//		client.close();
//		new MongoDbController().dropTable("user");
//		new MongoDbController().dropTable("server");
//		new MongoDbController().dropTable("online");
//		new MongoDbController().dropTable("dialog");
//		new MongoDbController().dropTable("message");
//		MongoCollection<Document> table = new MongoDbConroller().getTableInfo("login");
//		table.insertOne(new Document().append("user", "admin").append("pwd", "666666").append("type","2"));
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("hello");
//		list.add("world");
//		list.add("java");
//		System.out.println(list);

	}
}

