package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
	/*
	 * 返回当前系统时间，格式	年-月-日-小时-分-秒
	 */
	public static String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return df.format(date);
	}
}
