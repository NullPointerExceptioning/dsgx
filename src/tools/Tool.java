package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
	/*
	 * ���ص�ǰϵͳʱ�䣬��ʽ	��-��-��-Сʱ-��-��
	 */
	public static String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return df.format(date);
	}
}
