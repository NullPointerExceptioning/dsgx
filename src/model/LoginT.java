package model;

import org.bson.Document;

/*
 * µÇÂ¼±í
 * user	µÇÂ¼ÕËºÅ
 * pwd	µÇÂ¼ÃÜÂë
 * type	µÇÂ¼ÕßÀàÐÍ
 */
public class LoginT {
	private String user;
	private String pwd;
	private String type;
	
	public LoginT(){
		this.user = "";
		this.pwd = "";
		this.type = "1";
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
