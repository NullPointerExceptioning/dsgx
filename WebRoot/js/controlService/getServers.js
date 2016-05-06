
/**
 * controlService/getOnline		模块1
 * controlService/getRecords	模块2
 * controlService/getServices	模块3	
 */
var xmlhttp1;	//xmlHttpRequest object
var xmlhttp2;
var getInforesult;
var model3={
		infoUrl:"http://localhost:8080/dsgx/website/GetServers",
		//主函数，获取客服信息
		getInfo:function(){
			xmlhttp1 = new XMLHttpRequest();
			xmlhttp1.onreadystatechange = function(){		
				//回调函数
				if(xmlhttp1.readyState==4 && xmlhttp1.status==200){
					getInforesult = eval(xmlhttp1.responseText);
					tool3.updateInfo(getInforesult);
				};			
				}
			xmlhttp1.open("POST", this.infoUrl,true);
			xmlhttp1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp1.send();
		},
		registUrl:"http://localhost:8080/dsgx/website/WebRegist",
		//显示注册界面
		add:function(){
			var manage = document.getElementById("manage");
			var form = document.getElementById("regsitForm");
			manage.style.display = "none";
			form.style.display = "block";
		},
		//注册事件，Ajax
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
			xmlhttp2 = new XMLHttpRequest();
			var registResult;
			xmlhttp2.onreadystatechange = function(){
				if(xmlhttp2.readyState==4 && xmlhttp2.status==200){
					var registResult = xmlhttp2.responseText;
					if(registResult=="success"){
						alert("注册成功！");
						//先删除原先数据，再重新加载
						tool3.addregisterInfo(register);
						tool3.view("manage");
					}
					else
						alert("注册失败，请重新尝试..");
				}
			}
			xmlhttp2.open("POST", this.registUrl,true);	
			var info = "register="+JSON.stringify(register);
			xmlhttp2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp2.send(info);
		}
		
}
/*
 * model3 辅助模块
 */
var tool3 = {
		//将新注册的用户加入到显示列表中
		addregisterInfo:function(register){
			var div = document.createElement("div");
			div.className = "col-xs-4 col-lg-4";
			var h2 = document.createElement("h2");
			var h2text = document.createTextNode(register.name);
			h2.appendChild(h2text);
			var p1 = document.createElement("p");
			var p1text = document.createTextNode("新客服显示啦。。。。");
			p1.appendChild(p1text);
			var p2 = document.createElement("p");
			var a = document.createElement("a");
			a.className = "btn btn-default";
			a.href = "javascript:void(0)";
			p2.appendChild(a);
			var atext = document.createTextNode("编辑");
			a.appendChild(atext);
			div.appendChild(h2);
			div.appendChild(p1);
			div.appendChild(p2);							
			var add = document.getElementById("add");
			var manage = document.getElementById("manage");
			manage.insertBefore(div, add);			
		},
		//主界面List操作。显示list对应界面
		view :function(opt){
			var status = document.getElementById("status");
			var manage = document.getElementById("manage");
			var record = document.getElementById("record");	
			var listS = document.getElementById("listS");
			var listM = document.getElementById("listM");
			var listR = document.getElementById("listR");
			var form = document.getElementById("regsitForm");
			form.style.display = "none";
			//显示板块
			status.style.display = (opt=="status") ?  "block" : "none";
			manage.style.display = (opt=="manage") ?  "block" : "none";
			record.style.display = (opt=="record") ?  "block" : "none";
			//改变list样式
			switch(opt){
				case "status":
					listS.className = "list-group-item active";
					listM.className = "list-group-item";
					listR.className = "list-group-item";
					break;
				case "manage":
					listM.className = "list-group-item active";
					listS.className = "list-group-item";
					listR.className = "list-group-item";
					break;
				case "record":
					listR.className = "list-group-item active";
					listM.className = "list-group-item";
					listS.className = "list-group-item";
					break;
			}
		},
		//将从服务器读取的客服信息显示在页面中
		updateInfo:function(getInforesult){
			for(var i=0;i<getInforesult.length;i++){
				var div = document.createElement("div");
				div.className = "col-xs-4 col-lg-4";
				var h2 = document.createElement("h2");
				var h2text = document.createTextNode(getInforesult[i].nickName);
				h2.appendChild(h2text);
				var p1 = document.createElement("p");
				var p1text = document.createTextNode("新客服显示啦。。。。");
				p1.appendChild(p1text);
				var p2 = document.createElement("p");
				var a = document.createElement("a");
				a.className = "btn btn-default";
				a.href = "javascript:void(0)";
				p2.appendChild(a);
				var atext = document.createTextNode("编辑");
				a.appendChild(atext);
				div.appendChild(h2);
				div.appendChild(p1);
				div.appendChild(p2);							
				var add = document.getElementById("add");
				var manage = document.getElementById("manage");
				manage.insertBefore(div, add);
			};
		}

}


