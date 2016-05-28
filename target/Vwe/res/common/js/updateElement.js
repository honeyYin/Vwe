

function getE(v){return document.getElementById(v);}  

function addNewSection(parentId){//传入元素id
	  
	  var parent=getE(parentId);//获取父元素
	  var divName='div-section'+sectionId;//div容器的名称
	  var sectionName = "版块"+sectionId;
	  
	  var oDiv=document.createElement("div");
	  oDiv.id = divName;
	 
	  var divHtml = "<table width=\"100%\"  cellpadding=\"2\" cellspacing=\"1\" border=\"0\" ><tr><td width='10%'  class='pn-flabel pn-flabel-h'>"+sectionName+":</td>"+
	  "<td colspan='3' width='90%' class='pn-fcontent'>"+
	  "<input type='button' value='添加小节'  onclick='javascript:addNewPara("+sectionId+")' /> &nbsp; "+
	  "<input type='button' value='添加区域跳转块'  onclick='javascript:addNewOutLink("+sectionId+")' /> &nbsp; "+
	  "</td></tr><tr><td width='10%'  class='pn-flabel pn-flabel-h'><span class='pn-frequired'>*</span>版块标题:</td>"+
	  "<td colspan='3' width='90%' class='pn-fcontent'>"+
	  "<input id='sectionTitle"+sectionId+"'  type='text' maxlength='150' name='sectionTitle"+sectionId+"' class='required' size='70' maxlength='150'/>"+
	  "<input type='button' value='删除'  onclick='javascript:deleteElement(\""+parentId+"\",\""+divName+"\",\"section\")'/> &nbsp;</td></tr></table>";
//	  var content = parent.innerHTML;
//	  parent.innerHTML =content+divHtml;
	  oDiv.innerHTML = divHtml;
	  parent.appendChild(oDiv);
	  sectionId = sectionId+1;
}
function addNewPara(parentId){  //传入数据编号即可
	  
	  var parent=getE('div-section'+parentId);//获取父元素
	  var paraId =arrPara[parentId];
	  var divName='div-para'+parentId+"-"+paraId;//div容器的名称
	  var paraName = "小节"+parentId+"-"+paraId;
	  

	  var oDiv=document.createElement("div");
	  oDiv.id = divName;
	  
	  var divHtml ="<table width=\"100%\"  cellpadding=\"2\" cellspacing=\"1\" border=\"0\"><tr><td width='10%'  class='pn-flabel pn-flabel-h'>"+paraName+":</td>"+
			"<td colspan='3' width='90%' class='pn-fcontent'></td></tr><tr>"+
			"<td width='10%'  class='pn-flabel pn-flabel-h'><span class='pn-frequired'>*</span>小节标题:</td>"+
			"<td colspan='3' width='90%' class='pn-fcontent'>"+
			"<input id='paraTitle"+parentId+"-"+paraId+"'  type='text' maxlength='150' name='paraTitle"+parentId+"-"+paraId+"' class='required' size='70' maxlength='150'/>"+
			"<input type='button' value='删除'  onclick='javascript:deleteElement("+parentId+","+paraId+",\"para\")'/> &nbsp; "+
			"</td></tr><tr ><td width='10%' class='pn-flabel pn-flabel-h'>小节正文:</td>"+
			"<td colspan='3' width='90%' class='pn-fcontent'>"+
			"	<textarea  id='paraContent"+parentId+"-"+paraId+"' name='paraContent"+parentId+"-"+paraId+"' cols='70' rows='10'></textarea>"+
			"</td></tr><tr  > <td width='10%' class='pn-flabel'>小节配图:</td>"+
			 "<td colspan='1' width='40%' class='pn-fcontent'>"+
			 "       <input class='button'  type='button' id='paraBtnUploadFile"+parentId+"-"+paraId+"' value='上传图片' onclick=\"javascript:uploanFile('paraBtnUploadFile"+parentId+"-"+paraId+"'," +
			 		"'paraPic"+parentId+"-"+paraId+"','paraTitleImg"+parentId+"-"+paraId+"','paraPicDele"+parentId+"-"+paraId+"','para','-1')\"/>"+
			"       <input type='hidden' id='paraTitleImg"+parentId+"-"+paraId+"' name='paraTitleImg"+parentId+"-"+paraId+"' value=\"\"/>"+
			 "       <span class='pn-fhelp' id='paraPic"+parentId+"-"+paraId+"'>无图片</span> <span id = 'paraPicDele"+parentId+"-"+paraId+"'></span></td></tr></table>";
//	  var content = parent.innerHTML;
//	  parent.innerHTML =content+divHtml;

	  oDiv.innerHTML = divHtml;
	  parent.appendChild(oDiv);
	  
	  arrPara[parentId-1] = paraId+1;
}
function addNewOutLink(parentId){

	  var parent=getE('div-section'+parentId);//获取父元素
	  var outlinkId = arrOutLink[parentId];
	  var divName='div-outLink'+parentId+"-"+outlinkId;//div容器的名称
	  var outLinkName = "跳转区域"+parentId+"-"+outlinkId;
	  
	  var oDiv=document.createElement("div");
	  oDiv.id = divName;
	  
	  var divHtml ="<table width=\"100%\"  cellpadding=\"2\" cellspacing=\"1\" border=\"0\" ><tr><td width='10%'  class='pn-flabel pn-flabel-h'>"+outLinkName+":</td>"+
		"<td colspan='3' width='90%' class='pn-fcontent'></td></tr><tr >"+
		"<td width='10%'  class='pn-flabel pn-flabel-h'><span class='pn-frequired'>*</span>标题:</td>"+
		"<td colspan='3' width='90%' class='pn-fcontent'>"+
		"<input id='outTitle"+parentId+"-"+outlinkId+"'  type='text' maxlength='150' name='outTitle"+parentId+"-"+outlinkId+"' class='required' size='70' />"+
		"<input type='button' value='删除' onclick='javascript:deleteElement("+parentId+","+outlinkId+",\"outLink\")'/> &nbsp; </td></tr><tr>"+
		"<td width='10%' class='pn-flabel pn-flabel-h'>副标题:</td>"+
		"<td colspan='3' width='90%' class='pn-fcontent'>"+
		"	<input id='outSecTitle"+parentId+"-"+outlinkId+"'  type='text' maxlength='150' name='outSecTitle"+parentId+"-"+outlinkId+"' class='required' size='70' />"+
		"</td></tr><tr><td width='10%' class='pn-flabel pn-flabel-h'>金额:</td>"+
		"<td colspan='3' width='90%' class='pn-fcontent'>"+
		"	<input id='outPrize"+parentId+"-"+outlinkId+"'  type='text' maxlength='50' name='outPrize"+parentId+"-"+outlinkId+"' class='required' size='20' />"+
		"</td></tr><tr ><td width='10%' class='pn-flabel pn-flabel-h'>链接:</td>"+
		"<td colspan='3' width='90%' class='pn-fcontent'>"+
		"	<input id='outUrl"+parentId+"-"+outlinkId+"'  type='text' maxlength='150' name='outUrl"+parentId+"-"+outlinkId+"' class='required' size='70' /></td></tr></div></table>";
//	  var content = parent.innerHTML;
//	  parent.innerHTML =content+divHtml;
	  oDiv.innerHTML = divHtml;
	  parent.appendChild(oDiv);
	  
	  arrOutLink[parentId-1] = outlinkId+1;

}
function deleteElement(parentId,deleId,deleType){
	if(deleType == 'section'){
		 var parent=getE(parentId);
		 parent.removeChild(getE(deleId)); 
//		 sectionId = sectionId-1;
	}else if(deleType == 'para'){
		 var parent=getE('div-section'+parentId);
		 var dele ='div-para'+parentId+"-"+deleId;//div容器的名称
		 parent.removeChild(getE(dele));
//		 arrPara[parentId-1] =  arrPara[parentId-1]-1;

	}else if(deleType == 'outLink'){
		var parent=getE('div-section'+parentId);
		var dele ='div-outLink'+parentId+"-"+deleId;//div容器的名称
		parent.removeChild(getE(dele));
//		arrOutLink[parentId-1] = arrOutLink[parentId-1]-1;

	}else{
		
	}
	 
}
function deleteElementAndDB(parentId,deleId,deleType,dbId){
	if(deleType == 'section'){
		 var parent=getE(parentId);
		 parent.removeChild(getE(deleId)); 
//		 sectionId = sectionId-1;
		 
		 var par_data="sectionId="+dbId;
			$.ajax({ 
				 type: "POST", 
				 url: "deleteSection",  
				 data: par_data, 
				 success: function(message){ 
//					alert(message);
			}});
	}else if(deleType == 'para'){
		 var parent=getE('div-section'+parentId);
		 var dele ='div-para'+parentId+"-"+deleId;//div容器的名称
		 parent.removeChild(getE(dele));
//		 arrPara[parentId-1] =  arrPara[parentId-1]-1;
		//删除数据库中的数据
			var par_data="paraId="+dbId;
			$.ajax({ 
				 type: "POST", 
				 url: "deletePara",  
				 data: par_data, 
				 success: function(message){ 
					//alert(message);
			}});		 
	}else if(deleType == 'outLink'){
		var parent=getE('div-section'+parentId);
		var dele ='div-outLink'+parentId+"-"+deleId;//div容器的名称
		parent.removeChild(getE(dele));
//		arrOutLink[parentId-1] = arrOutLink[parentId-1]-1;
		//删除数据库中的数据
		var par_data="outLinkId="+dbId;
		$.ajax({ 
			 type: "POST", 
			 url: "deleteOutLink",  
			 data: par_data, 
			 success: function(message){ 
				//alert(message);
		}});
	}else{
		
	}
}

