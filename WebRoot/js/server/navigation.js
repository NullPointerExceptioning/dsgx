/**
 *adviser/dialog	->model1	聊天模块
 *adviser/navigation->model2	导航模块
 *server/service    ->model3    后台服务模块
 */
var model2={
		view:function(opt){
			var home = document.getElementById("home");
			var record = document.getElementById("record");
			var dialog = document.getElementById("dialog");
			var template = document.getElementById("leftmodel");
			var chat = document.getElementById("chat");
			
			switch(opt){
			case "home":
				window.location.href = "index.html";
				break;
			case "record":
				window.location.href = "records.html";
				break;
			case "dialog":
				window.location.href = "server.html";
				break;
			}
		}
}
