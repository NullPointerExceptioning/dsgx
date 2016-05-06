/**
 *adviser/dialog->model1		聊天模块
 *adviser/navigation->model2	导航模块
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
				home.className = "list-group-item active";
				record.className = "list-group-item";
				dialog.className = "list-group-item";
				template.style.display = "block";
				chat.style.display = "none";
				break;
			case "record":
				record.className = "list-group-item active";
				home.className = "list-group-item";
				dialog.className = "list-group-item";
				template.style.display = "block";
				chat.style.display = "none";
				break;
			case "dialog":
				dialog.className = "list-group-item active";
				home.className = "list-group-item";
				record.className = "list-group-item";
				template.style.display = "none";
				chat.style.display = "block";
				break;
			}
		}
}
