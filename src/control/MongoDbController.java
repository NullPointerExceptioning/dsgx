package control;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MongoDbController {
	//程序使用的数据库名
	private String dbName = "dsgx2";
	
	/*
	 * 读mongodb数据库，返回需要的表。
	 * tName	读取的数据表的名称
	 */
//	@SuppressWarnings("resource")
//	public MongoCollection<Document> getTableInfo(String tName){
//		MongoDatabase mdb = new MongoClient("localhost").getDatabase(dbName);
//		MongoCollection<Document> mc = mdb.getCollection(tName);
//		return mc;
//	}
	/*
	 * 删除table表
	 */
//	public void dropTable(String table){
//		new MongoDbController().getTableInfo(table).drop();
//	}
//
//	/*
//	 * insert one data into table
//	 */
//	public static void insertOneData(String table, Document document) {
//		MongoCollection<Document> mc = MongoDbConroller.connectCollection(table);
//		mc.insertOne(document);
//	}
//
//	/*
//	 * insert many data
//	 */
//	public void insertManyDatas(String table, List<Document> documents) {
//		MongoCollection<Document> mc = MongoDbConroller.connectCollection(table);
//		mc.insertMany(documents);
//	}
//
//	/*
//	 * delete all datas
//	 */
//	public static void deleteAllDatas(String table) {
//		MongoDbConroller.connectCollection(table).deleteMany(new Document());
//	}
//
//	/**
//	 * delete db dangerous!!!!!!!!!!!
//	 */
//	public static void destroyDB() {
//		md.drop();
//	}
//
//	/*
//	 * find data from table
//	 */
//	public static Document showAllData(String table) {
//		MongoCursor<Document> cursor = MongoDbConroller.connectCollection(table).find()
//				.iterator();
//		try {
//			while (cursor.hasNext())
//				System.out.println(cursor.next().toJson());
//		} finally {
//			cursor.close();
//		}
//		return new Document();
//	}
//
//	/*
//	 * update data in table
//	 */
//	public int updateData(String table, Document d) {
//
//		return 1;
//	}
//
//	/*
//	 * return userId else return error(0)
//	 */
//	public static int queryUserId(String account) {
//		System.out.println("query userId...");
//		BasicDBObject obj = new BasicDBObject();
//		obj.put("account", account);
//		MongoCursor<Document> cursor = MongoDbConroller.connectCollection("user")
//				.find(obj).iterator();
//		if (cursor.hasNext())
//			return (Integer) cursor.next().get("_id");
//		else
//			return 0;
//	}

}
