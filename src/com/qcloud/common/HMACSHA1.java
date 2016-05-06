package com.qcloud.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA1 {

	private static final String HMAC_SHA1 = "HmacSHA1";

	public static byte[] getSignature(String data, String key) throws Exception {
		return getSignature(data.getBytes(), key);
	}

	public static byte[] getSignature(byte[] data, String key) throws Exception {
		Mac mac = Mac.getInstance(HMAC_SHA1);
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				mac.getAlgorithm());
		mac.init(signingKey);
		return mac.doFinal(data);
	}

	public static String getFileSha1(String path) throws IOException {
		File file = new File(path);
		FileInputStream in = new FileInputStream(file);
		MessageDigest messagedigest;
		try {
			messagedigest = MessageDigest.getInstance("SHA-1");

			byte[] buffer = new byte[1024 * 1024 * 10];
			int len = 0;

			while ((len = in.read(buffer)) > 0) {
				// �ö���ͨ��ʹ�� update����������������
				messagedigest.update(buffer, 0, len);
			}

			// ���ڸ��������ĸ������ݣ�digest ����ֻ�ܱ�����һ�Ρ��ڵ��� digest ֮��MessageDigest
			// �����������ó����ʼ״̬��
			return getFormattedText(messagedigest.digest());//byte2hex(messagedigest.digest());
		} catch (NoSuchAlgorithmException e) {
			//NQLog.e("getFileSha1->NoSuchAlgorithmException###", e.toString());
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			//NQLog.e("getFileSha1->OutOfMemoryError###", e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			in.close();
		}
		return null;
	}
	

	private static String getFormattedText(final byte[] bytes) {
		char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// ������ת����ʮ�����Ƶ��ַ�����ʽ
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}

