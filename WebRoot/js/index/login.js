/**
 * login/login->model	登录模块
 */
/*
 * function loign()	//登录功能 
 */
var model = {
	loginUrl: httpUrl+"/dsgx/website/WebLogin",
	login: function() {
		//登录按钮
		var login = document.getElementById("login");
		login.innerHTML = "登录中...";
		//user 用户名
		var user = document.getElementById("user");
		//pwd 密码
		var pwd = document.getElementById("pwd");
		//request 发送的请求
		var request = "user=" + escape(user.value) + "&pwd=" + escape(pwd.value);
		var xmlhttp = new XMLHttpRequest();
		//设置回调函数
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				var result = xmlhttp.responseText;
				tool.Goto(result);
			}
		};
		xmlhttp.open("POST", this.loginUrl, true);
		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xmlhttp.send(request);	
	}
}

/*
 * function Goto() //根据服务器返回的数据，选择跳转的页面
 */
var tool = {
		Goto:function(result){
			var login = document.getElementById("login");
			//登录失败
			if(result==0){
				alert("账号密码错误,登录失败!");
				login.innerHTML = "登录";
			}//登录到客服页面
			else if(result==1){
				window.location = "html/server/index.html";
			}//登录到经理页面
			else if(result==2){
				window.location = "html/manager/manager.html";
			}
		}
}