/*
 *注册新客服 
 */
var xmlhttp;
var regist = {
		url : httpUrl+"/dsgx/website/WebRegist",
		add :function(){
			var manage = document.getElementById("manage");
			var form = document.getElementById("regsitForm");
			manage.style.display = "none";
			form.style.display = "block";
		},
		regist:function(){
			var user = document.getElementById("user").value;
			var pwd = document.getElementById("pwd").value;
			var sex = document.getElementById("sex").value;
			var email = document.getElementById("email").value;
			var name = document.getElementById("name").value;
			var type = document.getElementById("type").value;
			var age = document.getElementById("age").value;
			var register = {};
			register.user = user;
			register.pwd = pwd;
			register.sex = sex;
			register.email = email;
			register.name = name;
			register.type = type;
			register.age = age;
			xmlhttp = new XMLHttpRequest();	
			xmlhttp.onreadystatechange = this.checkCallBack;
			xmlhttp.open("POST", this.url,true);	
			var info = "register="+JSON.stringify(register);
			xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp.send(info);
		},
		checkCallBack:function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				alert(xmlhttp.responseText);
			}
		}
}
