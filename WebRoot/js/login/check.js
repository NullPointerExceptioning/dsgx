	//公用工具，登录验证
	function loginCheck(){
		var xmlhttp = new XMLHttpRequest();
		var url = httpUrl + "/dsgx/website/CheckLogin";
		xmlhttp.open("POST", url);
		xmlhttp.send(null);
		if(xmlhttp.status==200){
			if(xmlhttp.responseText=="-1"){
				window.location = "../../index.html";
			}
		}
	}
	loginCheck();