/**
 *adviser/dialog	->model1	聊天模块
 *adviser/navigation->model2	导航模块
 *server/service    ->model3    后台服务模块
 */
var model3 = {
		queryService:function(){
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					var result = xmlhttp.responseText;
					if(result=="1"){
						//启动queryMessage	
						if(message==null){
							message = window.setInterval(this.queryMessage,10*1000);
						}
					}else if(result=="-1"){
						//提供会话已经结束，刷新界面
						//停止queryMessage
						if(message!=null){
							window.clearInterval(message);
							message = null;
						}	
					}
				}
			}
			var url = httpUrl + "/dsgx/website/AdviserAskService";
			xmlhttp.open("POST",url);
			xmlhttp.send(null);	
		},
		queryMessage:function(){
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					var result = xmlhttp.responseText;
					//当前没有新消息
					if(result=="-1"){
						//do nothing
					}//当前会话已断开
					else if(result=="-2"){
					}//收到新消息
					else{
						//解析json包
						var json = JSON.parse(result);
						//分析每一条数据，并分类显示
						var i = 0;
						for(;i<json.length;i++){
							let type = json[i].messageType;
							if(type=="TEXT"){
								tool1.displayUserMessage(json[i].message);
							}
							if(type=="VIDEO"){
								tool1.displayUserVideo(json[i].messageUrl);
							}
							if(type=="IMAGE"){
								tool1.displayUserImage(json[i].messageUrl);
							}
						}
						//如果用户不在当前页面，对用户做出提醒
						if(document.visibilityState!="visible"){
							if(!("Notification"in window)){
								alert("浏览器不支持通知功能，请联系管理员lich@mail.dlut.edu.cn");
							}else if(Notification.permission=="granted"){
								var notification = new Notification("你有新的消息，请注意查收!");
							}else if(Notification.permission!="denied"){
								Notification.requestPermission(function (permission){
									if(permission ==="granted"){
										var notification = new Notification("你有新的消息，请注意查收！");
									}
								});
							}
							alert("开工啦!");
						}
					}
				}

			}
			var url = httpUrl + "/dsgx/website/AdviserAskMessage";
			xmlhttp.open("POST", url,true);
			xmlhttp.send(null);
		}
		
}
var service = window.setInterval(model3.queryService,30*1000);
var message = null;
tool1.displayUserVideo("http://www.w3schools.com/html/mov_bbb.mp4");
