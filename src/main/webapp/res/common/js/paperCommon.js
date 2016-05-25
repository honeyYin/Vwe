function deleteImg(type,id,picId,titleImgId,spanId){
	if(type == null || type ==""  ){
		alert("参数非法");
	}
	if(id == null || id =="" || id=="-1"){
		 document.getElementById(picId).innerHTML = "无图片";
		 document.getElementById(titleImgId).value = "";
		 document.getElementById(spanId).innerHTML="";
	}else{
		var par_data="type="+type+"&id="+id;
		$.ajax({ 
			 type: "POST", 
			 url: "deleteImg",  
			 data: par_data, 
			 success: function(message){ 
				 if(message =="succ"){
					 document.getElementById(picId).innerHTML = "无图片";
					 document.getElementById(titleImgId).value = "";
					 document.getElementById(spanId).innerHTML="";
				 }
		}});
	}
	
}