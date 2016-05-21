package control;

import org.bson.Document;

import tools.Values;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.LoginT;
import model.ServerT;

public class LoginC {
	
	/*
	 * ��login��ת��ΪDocument
	 */
	public static Document convertDoc(LoginT login){
		Document doc = new Document()
			.append("user", login.getUser())
			.append("pwd",login.getPwd())
			.append("type", login.getType());
		return doc;
	}
	
	/*
	 * �жϵ�¼���
	 * 0	ʧ��
	 * 1	�ɹ�����¼���ǿͷ�
	 * 2	�ɹ�����¼���Ǿ���
	 */
	@SuppressWarnings("finally")
	public static int checkLogin(LoginT login){
		System.out.println("checkLogin...");///////////
		//���ص�����ֵ
		int type = 0;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		MongoCursor<Document> cursor = null;
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("user", login.getUser());
			query.put("pwd", login.getPwd());
			cursor = db.getCollection("login")
					.find(query).iterator();
			if(cursor.hasNext()){
				Document doc = cursor.next();
				type = Integer.parseInt((String) doc.get("type"));
				/*
				 * ���ͷ�״̬��Ϊonline
				 * �л����getResult �޸�һ��������
				 */
				if(type==1){
					OnlineC.beOnline((String)doc.getString("user"));
				}
			}
			System.out.println("type:"+type);///////////////
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
			client.close();
			return type;
		}

	}
	/*
	 * �ɹ�ע���¿ͷ�֮�󽫿ͷ���Ϣ��¼login��
	 * register	�ͷ���Ϣ
	 * return 1	�ɹ�
	 */
	@SuppressWarnings("finally")
	public static int addLoginInfo(ServerT register){
		//���
		int result = 0;
		//mongodb �ͻ���
		MongoClient client = new MongoClient("localhost");
		//mongodb ���ݿ�
		MongoDatabase db = client.getDatabase(Values.dbName);
		try{
			MongoCollection<Document> table = db.getCollection("login");
			LoginT login = new LoginT();
			login.setUser(register.getUser());
			login.setPwd(register.getPwd());
			table.insertOne(LoginC.convertDoc(login));		
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.close();
			return result;
		}


	}
	

}
