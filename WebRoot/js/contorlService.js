/*
* 根据用户选择的list选项，提供不同的页面布局。
*/
var controlS = {
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
		}
}