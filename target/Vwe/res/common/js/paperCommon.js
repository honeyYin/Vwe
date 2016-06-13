function deleteImg(type,id,picId,titleImgId,spanId){
	if(type == null || type ==""  ){
		alert("参数非法");
	}
	if(id == null || id =="" || id=="-1"){
		 document.getElementById(picId).innerHTML = "无图片";
		 document.getElementById(titleImgId).value = "";
		 document.getElementById(spanId).innerHTML=" ";
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
					 document.getElementById(spanId).innerHTML=" ";
				 }
		}});
	}
	
}
//jquery选中所有checkbox
function jqselectCheck(){  //jquery获取复选框值
		 if (document.getElementById("allids").checked) {  
                $("input[name='ids']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox   
                            $(this).attr("checked", true);  
                        })  
            } else {   //反之 取消全选    
                $("input[name='ids']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox   
                            $(this).attr("checked", false);  
                        })  
            }  

}

