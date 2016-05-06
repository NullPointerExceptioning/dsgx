/**
 *adviser/navigation->model2	聊天模块
 *adviser/dialog	->model1	导航模块
 */

var model1={
		//发送信息的地址
		sendUrl:"http://localhost:8080/dsgx/website/AdviserSendMessage",
		send:function(){
			var text = tool1.getMessage();
			if(text.trim()=="")
				return;
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					tool1.callBack(xmlhttp.responseText);
				}
			}
			xmlhttp.open("POST", this.sendUrl,true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send("message="+text);
		}
}



var tool1={
		getMessage:function(){
			var content = document.getElementById("input").value;
			return content;
		},
		callBack:function(text){
			if(text=="1"){
				var content = document.getElementById("input");
				this.displayAdviserMessage(content.value);
				content.value = "";
			}else{
				//提示发送不成功，重新发送
				alert("会话已关闭");
			}
		},
		displayAdviserMessage:function(text){
			text = tool1.changeForm(text);
			var label = document.createElement("label");
			label.innerHTML = text;
			var img = document.createElement("img");
			img.src = "img/adviser.jpg";
			label.className = "adviserLabel";
			img.className = "adviserImg";
			var t = document.getElementById("chatTable").insertRow();
			t.className = "adviserMessage";
			t.appendChild(label);
			t.appendChild(img);
			var content = document.getElementById("content");
			content.scrollTop = content.scrollHeight;			
		},
		displayUserMessage:function(text){
			var label = document.createElement("label");
			label.appendChild(document.createTextNode(text));
			var img = document.createElement("img");
			img.src = "img/atm.jpg";
			label.className = "userLabel";
			img.className = "userImg";
			var t = document.getElementById("chatTable").insertRow();
			t.className = "userMessage";
			t.appendChild(img);
			t.appendChild(label);
			var content = document.getElementById("content");
			content.scrollTop = content.scrollHeight; 
		}
		,
		displayUserVideo:function(url){
			var v = document.createElement("video");
			var img = document.createElement("img");
			v.src = "url";
			img.src = "img/atm.jpg";
			v.className = "userLabel";
			img.className = "userImg";
			var t = document.getElementById("chatTable").insertRow();
			t.className = "userMessage";
			t.appendChild(img);
			t.appendChild(v);
			var content = document.getElementById("content");
			content.scrollTop = content.scrollHeight; 
			
		},
		displayUserImage:function(url){
			var i = document.createElement("img");
			var img = document.createElement("img");
			i.src = "url";
			img.src = "img/atm.jpg";
			i.className = "userLabel";
			img.className = "userImg";
			var t = document.getElementById("chatTable").insertRow();
			t.className = "userMessage";
			t.appendChild(img);
			t.appendChild(i);
			var content = document.getElementById("content");
			content.scrollTop = content.scrollHeight; 	
		},
    	setChatHeight:function setchatHeigh(){
       		var height = 2*(window.outerHeight/3);
       		height = height + "px";
       		document.getElementById("chat").style.height = height;
    	},
    	changeForm:function(text){
    		var result="";
    		list = text.split("\n");
    		var i =0;
    		for(;i<list.length-1;i++){
    			result+=list[i]+"<br>";
    		}
    		result+=list[i];
    		return result;
    	}
}
//立即执行
tool1.setChatHeight();