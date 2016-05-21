package com.qcloud.api;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONObject;

import com.qcloud.common.HMACSHA1;
import com.qcloud.common.Request;
import com.qcloud.common.Sign;


public class VideoCloud {
	/**
	 * @brief Cos��
	 * @author robinslsun
	 */
	final String VIDEO_CGI_URL = "http://web.video.myqcloud.com/files/v1/";
	public enum FolderPattern {File, Folder, Both};
	private int appId;
	private String secretId;
	private String secretKey;
	private int timeOut;
	
	/**
	 * VideoCloud ���췽��
	 * @param appId			��Ȩappid
	 * @param secretId		��Ȩsecret id
	 * @param secretKey	 ��Ȩsecret key
	 */
	public VideoCloud(int appId, String secretId, String secretKey){
		this(appId, secretId, secretKey, 60);
	}
	
	/**
	 * VideoCloud ���췽��
	 * @param appId			��Ȩappid
	 * @param secretId		��Ȩsecret id
	 * @param secretKey	 ��Ȩsecret key
	 * @param timeOut	���糬ʱ
	 */
	public VideoCloud(int appId, String secretId, String secretKey, int timeOut){
		this.appId = appId;
		this.secretId = secretId;
		this.secretKey = secretKey;
		this.timeOut = timeOut * 1000;
	}
	
	/**
	 * Զ��·��Encode����
	 * @param remotePath
	 * @return
	 */
	private String encodeRemotePath(String remotePath)
    {
		if(remotePath.equals("/")){
			return remotePath;
		}
		boolean endWith = remotePath.endsWith("/");
        String[] part = remotePath.split("/");
        remotePath = "";
        for(String s : part){
        	if (!s.equals(""))
            {
        		if(!remotePath.equals("")){
        			remotePath += "/";
        		}
                remotePath += URLEncoder.encode(s);
            }
        }
        remotePath = (remotePath.startsWith("/") ? "" : "/") + remotePath + (endWith ? "/" : "");
        return remotePath;
    }

	/**
	 * ��׼��Զ��·��
	 * @param remotePath Ҫ��׼����Զ��·��
	 * @return
	 */
    private String standardizationRemotePath(String remotePath)
    {
        if (!remotePath.startsWith("/"))
        {
            remotePath = "/" + remotePath;
        }
        if (!remotePath.endsWith("/"))
        {
            remotePath += "/";
        }
        return remotePath;
    }
	
	/**
	 * �����ļ�����Ϣ
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @param bizAttribute ������Ϣ
	 * @return
	 * @throws Exception 
	 */
	public String updateFolder(String bucketName, String remotePath, String bizAttribute) throws Exception{
		
		remotePath = standardizationRemotePath(remotePath);
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "update");
		data.put("biz_attr", bizAttribute);
		String sign = Sign.appSignOnce(appId, secretId, secretKey, (remotePath.startsWith("/") ? "" : "/") + remotePath, bucketName);
		String qcloud_sign = sign.toString();
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", qcloud_sign);
		header.put("Content-Type","application/json");
		return Request.sendRequest(url, data, "POST", header, timeOut);
	}
	
	/**
	 * �����ļ���Ϣ
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param videoCover ��Ƶ����
	 * @param bizAttribute ������Ϣ
	 * @param title ��Ƶ����
	 * @param desc ��Ƶ����
	 * @return
	 * @throws Exception 
	 */
	public String updateFile(String bucketName, String remotePath, String videoCover, String bizAttribute, String title, String desc) throws Exception{
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		int flag = 0;
		if (title != null && desc != null && bizAttribute != null && videoCover != null) {
			flag = 0x0f;
        } else {
            if (title != null) {
                flag |= 0x02;
            }
            if (desc != null) {
                flag |= 0x04;
            }
            if (bizAttribute != null) {
                flag |= 0x01;
            }
            if (videoCover != null) {
                flag |= 0x08;
            }
        }
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "update");
		data.put("video_cover", videoCover);
		data.put("biz_attr", bizAttribute);
		data.put("video_title", title);
		data.put("video_desc", desc);
		data.put("flag", flag);
		String sign = Sign.appSignOnce(appId, secretId, secretKey, (remotePath.startsWith("/") ? "" : "/") + remotePath, bucketName);
		String qcloud_sign = sign.toString();
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", qcloud_sign);
		header.put("Content-Type","application/json");
		return Request.sendRequest(url, data, "POST", header, timeOut);
	}
	
	/**
	 * ɾ���ļ���
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @return
	 * @throws Exception 
	 */
	public String deleteFolder(String bucketName, String remotePath) throws Exception{
		remotePath = standardizationRemotePath(remotePath);
		return deleteFile(bucketName, remotePath);
	}
	
	/**
	 * ɾ���ļ�
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·�����ļ�������ʽ/path/filename
	 * @return
	 * @throws Exception 
	 */
	public String deleteFile(String bucketName, String remotePath) throws Exception{
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "delete");
		String sign = Sign.appSignOnce(appId, secretId, secretKey, (remotePath.startsWith("/") ? "" : "/") + remotePath, bucketName);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", sign);
		header.put("Content-Type","application/json");
		return Request.sendRequest(url, data, "POST", header, timeOut);
	}
	
	/**
	 * ��ȡ�ļ�����Ϣ
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @return
	 * @throws Exception 
	 */
	public String getFolderStat(String bucketName, String remotePath) throws Exception{
		remotePath = standardizationRemotePath(remotePath);
		return getFileStat(bucketName, remotePath);
	}
	
	/**
	 * ��ȡ�ļ���Ϣ
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @return
	 * @throws Exception 
	 */
	public String getFileStat(String bucketName, String remotePath) throws Exception{
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "stat");
		long expired = System.currentTimeMillis() / 1000 + 60;
		String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", sign);
		return Request.sendRequest(url, data, "GET", header, timeOut);
	}
	
	/**
	 * �����ļ���
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @return
	 * @throws Exception 
	 */
	public String createFolder(String bucketName, String remotePath, String bizAttribute) throws Exception{
		remotePath = standardizationRemotePath(remotePath);
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "create");
		data.put("biz_attr", "bizAttribute");
		long expired = System.currentTimeMillis() / 1000 + 60;
		String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", sign);
		header.put("Content-Type","application/json");
		return Request.sendRequest(url, data, "POST", header, timeOut);
	}
	
	/**
	 * Ŀ¼�б�
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @param num ��ȡ������
	 * @param context ͸���ֶΣ��鿴��һҳ���򴫿��ַ���������Ҫ��ҳ����Ҫ��ǰһҳ����ֵ�е�context͸���������С�order����ָ����ҳ˳����order��0����ӵ�ǰҳ����/���·�ҳ����order��1����ӵ�ǰҳ����/���Ϸ�ҳ��
	 * @param order Ĭ������(=0), ��1Ϊ����
	 * @param pattern ��ȡģʽ:ֻ���ļ���ֻ���ļ��У�ȫ��
	 * @return
	 * @throws Exception 
	 */
	public String getFolderList(String bucketName, String remotePath, int num, String context, int order, FolderPattern pattern) throws Exception{
		remotePath = standardizationRemotePath(remotePath);
		return getFolderList(bucketName, remotePath, "", num, context, order, pattern);
	}
	
	/**
	 * Ŀ¼�б�,ǰ׺����
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ���·��
	 * @param prefix ��ȡ�ļ�/�ļ���ǰ׺
	 * @param num ��ȡ������
	 * @param context ͸���ֶΣ��鿴��һҳ���򴫿��ַ���������Ҫ��ҳ����Ҫ��ǰһҳ����ֵ�е�context͸���������С�order����ָ����ҳ˳����order��0����ӵ�ǰҳ����/���·�ҳ����order��1����ӵ�ǰҳ����/���Ϸ�ҳ��
	 * @param order Ĭ������(=0), ��1Ϊ����
	 * @param pattern ��ȡģʽ:ֻ���ļ���ֻ���ļ��У�ȫ��
	 * @return
	 * @throws Exception 
	 */
	public String getFolderList(String bucketName, String remotePath, String prefix, int num, String context, int order, FolderPattern pattern) throws Exception{
		remotePath = standardizationRemotePath(remotePath);
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath) + URLEncoder.encode(prefix);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "list");
		data.put("num", num);
		data.put("context", context);
		data.put("order", order);
		String[] patternArray = {"eListFileOnly", "eListDirOnly", "eListBoth"};
		data.put("pattern", patternArray[pattern.ordinal()]);
		long expired = System.currentTimeMillis() / 1000 + 60;
		String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", sign);
		return Request.sendRequest(url, data, "GET", header, timeOut);
	}
	
	/**
	 * �����ļ��ϴ�
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath �����ļ�·��
	 * @return
	 * @throws Exception 
	 */
	public String uploadFile(String bucketName, String remotePath, String localPath) throws Exception{
		
		try {			
			String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
			String sha1 = HMACSHA1.getFileSha1(localPath);
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("op", "upload");
			data.put("sha", sha1);
			long expired = System.currentTimeMillis() / 1000 + 60;
			String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("Authorization", sign);
			return Request.sendRequest(url, data, "POST", header, timeOut, localPath);			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * �����ļ��ϴ�
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath  �����ļ�·��
	 * @param videoCover �Զ�����Ƶ����URL
	 * @param bizAttribute �ļ����ԣ�ҵ���ά�� 
	 * @param title ��Ƶ�ı���
	 * @param desc ��Ƶ������
	 * @param magicContext ͸���ֶΣ�΢��Ƶ�Ὣ���ֶ���Ϣ͸����ҵ���趨�Ļص�url
	 * @return
	 * @throws Exception 
	 */
	public String uploadFile(String bucketName, String remotePath, String localPath, String videoCover, String bizAttribute, String title, String desc, String magicContext) throws Exception{
		
		try {			
			String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
			String sha1 = HMACSHA1.getFileSha1(localPath);
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("op", "upload");
			data.put("sha", sha1);
			data.put("video_cover", videoCover);
			data.put("biz_attr", bizAttribute);
			data.put("video_title", title);
			data.put("video_desc", desc);
			data.put("magicContext", magicContext);
			long expired = System.currentTimeMillis() / 1000 + 60;
			String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("Authorization", sign);
			return Request.sendRequest(url, data, "POST", header, timeOut, localPath);			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * ��Ƭ�ϴ���һ��
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath �����ļ�·��
	 * @param sliceSize ��Ƭ��С���ֽڣ�
	 * @return
	 * @throws Exception 
	 */
	public String sliceUploadFileFirstStep(String bucketName, String remotePath, String localPath, String videoCover, String bizAttribute,String title,String desc,String magicContext, int sliceSize) throws Exception{
		try{
			String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
			String sha1 = HMACSHA1.getFileSha1(localPath);
			long fileSize = new File(localPath).length();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("op", "upload_slice");
			data.put("sha", sha1);
			data.put("filesize", fileSize);
			data.put("slice_size", sliceSize);
			data.put("video_cover", videoCover);
			data.put("biz_attr", bizAttribute);
			data.put("video_title", title);
			data.put("video_desc", desc);
			data.put("magicContext", magicContext);
			long expired = System.currentTimeMillis() / 1000 + 60;
			String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("Authorization", sign);
			return Request.sendRequest(url, data, "POST", header, timeOut);				
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * ��Ƭ�ϴ���������
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath �����ļ�·��
	 * @param sessionId ��Ƭ�ϴ��ỰID
	 * @param offset �ļ���Ƭƫ����
	 * @param sliceSize  ��Ƭ��С���ֽڣ�
	 * @return
	 * @throws Exception 
	 */
	public String sliceUploadFileFollowStep(String bucketName, String remotePath, String localPath,
			String sessionId, int offset, int sliceSize) throws Exception{
		String url = VIDEO_CGI_URL + appId + "/" + bucketName + encodeRemotePath(remotePath);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("op", "upload_slice");
		data.put("session", sessionId);
		data.put("offset", offset);
		long expired = System.currentTimeMillis() / 1000 + 60;
		String sign = Sign.appSign(appId, secretId, secretKey, expired, bucketName);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", sign);
		return Request.sendRequest(url, data, "POST", header, timeOut, localPath, offset, sliceSize);
	}
	
	/**
	 * ��Ƭ�ϴ���Ĭ����Ƭ��СΪ512K
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath �����ļ�·��
	 * @return
	 * @throws Exception 
	 */
	public String sliceUploadFile(String bucketName, String remotePath, String localPath) throws Exception{
		return sliceUploadFile(bucketName, remotePath, localPath, "", "", "", "", "", 512 * 1024);
	}
	
	/**
	 * ��Ƭ�ϴ�
	 * @param bucketName bucket����
	 * @param remotePath Զ���ļ�·��
	 * @param localPath �����ļ�·��
	 * @param bizAttribute �ļ����ԣ�ҵ���ά�� 
	 * @param title ��Ƶ�ı���
	 * @param desc ��Ƶ������
	 * @param magicContext ͸���ֶΣ�΢��Ƶ�Ὣ���ֶ���Ϣ͸����ҵ���趨�Ļص�url
	 * @param sliceSize ��Ƭ��С���ֽڣ�
	 * @return
	 * @throws Exception 
	 */
	public String sliceUploadFile(String bucketName, String remotePath, String localPath, String videoCover, String bizAttribute,String title,String desc,String magicContext,int sliceSize) throws Exception{
		String result = sliceUploadFileFirstStep(bucketName, remotePath, localPath, videoCover, bizAttribute,title,desc,magicContext, sliceSize);
		try{
			JSONObject jsonObject = new JSONObject(result);
			int code = jsonObject.getInt("code");
			if(code != 0){
				return result;
			}
			JSONObject data = jsonObject.getJSONObject("data");
			if(data.has("access_url")){
				String accessUrl = data.getString("access_url");
				System.out.println("�����봫��" + accessUrl);
				return result;
			}
			else{
				String sessionId = data.getString("session");
				sliceSize = data.getInt("slice_size"); 
				int offset = data.getInt("offset");
				int retryCount = 0;
				while(true){
					result = sliceUploadFileFollowStep(bucketName, remotePath, localPath, sessionId, offset, sliceSize);
					System.out.println(result);
					jsonObject = new JSONObject(result);
					code = jsonObject.getInt("code");
					if(code != 0){
						//���ϴ�ʧ�ܺ������3��
						if(retryCount < 3){
							retryCount++;
							System.out.println("����....");
						}
						else{
							return result;
						}
					}
					else{
						data = jsonObject.getJSONObject("data");
						if(data.has("offset")){
							offset = data.getInt("offset") + sliceSize;
						}
						else{
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return "";
	}
}

