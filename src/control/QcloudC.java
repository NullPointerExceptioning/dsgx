package control;

import tools.Values;

import com.qcloud.api.VideoCloud;

public class QcloudC {
	public static final int appId = Values.AppId;
	public static final  String secretId = Values.secretId;
	public static final String secretKey = Values.secretKey;
	
	//��ȡ�ƶ�Ŀ¼����Ȩ
	public VideoCloud getCloud(){
		return new VideoCloud(appId,secretId,secretKey);
	}
	public String createFloder(String user){
		String result;
		try {
			result = new QcloudC().getCloud().createFolder("dsgx2", "/"+user+"/", null);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return result;
	}
	/*
	 //ͨ������̨��ȡAppId,SecretId,SecretKey
	public static final int APP_ID = 1000000;
	public static final String SECRET_ID = "SECRET_ID";
	public static final String SECRET_KEY = "SECRET_KEY";
	
	public static void main(String[] args) {
		VideoCloud video = new VideoCloud(APP_ID, SECRET_ID, SECRET_KEY);
		try{			
			String result = "";
			String bucketName = "abcde";
            String coverUrl = "http://ceshi-1000027.file.myqcloud.com/1.jpg";
            
            long start = System.currentTimeMillis();
			//����Ŀ¼
			result = video.createFolder(bucketName, "/sdk/","");
			System.out.println("=======createFolder========\n"+result);
			//�ϴ���Ƶ
			result = video.uploadFile(bucketName, "/sdk/test.mp4", "D:\\test.mp4");
			System.out.println("=======uploadFile========\n"+result);
			//result = video.uploadFile(bucketName, "/sdk/test.mp4", "D:\\test.mp4", "test file attr", "test title","test desc");
			//������Ƶ���ԡ����⡢��������Ϣ
			result = video.updateFile(bucketName, "/sdk/test.mp4", coverUrl, "test file attr", "test title","test desc");
			System.out.println("=======updateFile========\n"+result);
			//��ȡĿ¼����Ƶ�ļ���Ϣ
            result = video.getFolderList(bucketName, "/", 20, "", 0, VideoCloud.FolderPattern.Both);
            System.out.println("=======getFolderList========\n"+result);
            //��ȡ��Ƶ��Ϣ  
            result = video.getFileStat(bucketName, "/sdk/test.mp4");
            System.out.println("=======getFileStat========\n"+result);
			//����Ŀ¼����
            result = video.updateFolder(bucketName, "/sdk/", "test folder");
            System.out.println("=======updateFolder========\n"+result);
			//��ȡĿ¼����
            result = video.getFolderStat(bucketName, "/sdk/");
            System.out.println("=======getFolderStat========\n"+result);
			//ɾ����Ƶ�ļ�
            result = video.deleteFile(bucketName, "/sdk/test.mp4");
            System.out.println("=======deleteFile========\n"+result);
			//ɾ��Ŀ¼��Ŀ¼����Ϊ��
            result = video.deleteFolder(bucketName, "/sdk/");
            System.out.println("=======deleteFolder========\n"+result);
			//��Ƭ�ϴ��ļ�
            //result = video.sliceUploadFile(bucketName, "/sdk/big.mp4", "F:\\big.mp4");
			//result = video.sliceUploadFile(bucketName, "/sdk/big.mp4", "F:\\big.mp4", "test file attr", "test title","test desc",512 * 1024);
            long end = System.currentTimeMillis();
            System.out.println("����ʱ��" + (end - start) + "����");
			System.out.println("The End!");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	 */
}
