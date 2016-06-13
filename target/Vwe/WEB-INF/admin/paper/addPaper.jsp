<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/AjaxUpload.js"></script>
<!-- 添加页面元素 -->
<script type="text/javascript" src="<%=basePath%>res/common/js/addElement.js"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/paperCommon.js"></script>
<!-- 富文本编辑器 -->
<link rel="stylesheet" href="<%=basePath%>res/thirdparty/kindeditor/themes/default/default.css"/>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script>
<%-- KindEditor.ready(function(K) {

	var editor1 = K.create('#content', {

	cssPath : '<%=basePath%>res/thirdparty/kindeditor/plugins/code/prettify.css',

	uploadJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/upload_json.jsp',

	fileManagerJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/file_manager_json.jsp',

	allowFileManager : true
	})

}); --%>
function callAddNewsAction(){
	var type = $('#type')[0].value;
	var title = $('#title')[0].value;
	if(type == 1){
		var paperurl = $('#paperurl')[0].value;
		var otitleImg = $('#otitleImg')[0].value;
		if(title ==null || title ==""){
			alert("标题不能为空");
		}else if(otitleImg == null || otitleImg == ""){
			alert("标题图片不能为空");
		}else if(paperurl == null || paperurl ==""){
			alert("文章链接不能为空");
		}else{
			$("#newsForm")[0].submit();
		}
	}else{
		var titleImg = $('#titleImg')[0].value;
		var pregStage = $('#pregStage')[0].value;
		var recPregWeeks = $('#recPregWeeks')[0].value;
		var requires = $('.required');
		
		var r,re;
	    re = /\d*/i; //\d表示数字,*表示匹配多个数字
	    r = recPregWeeks.match(re);
	    
	    if(title ==null || title ==""){
			alert("标题不能为空");
		}else if(titleImg == null || titleImg == ""){
			alert("标题图片不能为空");
		}else if(pregStage == 2 && (r != recPregWeeks || recPregWeeks < 0 || recPregWeeks > 40)){
			alert("孕周填写不合法");
		}else{
			var outIllegal = 0;
			for(var n=0;n<requires.length;n++){
				var outname = requires[n].name;
				var outvalue = requires[n].value;
				if(outvalue == null || outvalue == ""){
					outIllegal = 1;
					break;
				}
			}
			if(outIllegal == 1){
				alert("有必填项未填写");
			}else{
				$("#newsForm")[0].submit();
			}
		}
	}
	
} 
function showrecPreg(){
	var pregStage = document.getElementById("pregStage").value;
	if(pregStage == 2){
		document.getElementById("div_recPreg").style.display="";
	}else{
		document.getElementById("div_recPreg").style.display="none";
	}
      
}
function clearNoNum(obj)
{
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function addOuterPaper(type){
	if(type == 1){
		document.getElementById("div-inpaper").style.display="none";
		document.getElementById("div-outpaper").style.display="block";
	}else{
		document.getElementById("div-inpaper").style.display="block";
		document.getElementById("div-outpaper").style.display="none";
	}
}
</script>

</head>

<body>
<div class="box-positon">
	<div class="rpos">当前位置: 文章管理 - 添加</div>
	<form class="ropt" action="<%=basePath%>paper/list" method="get">
		<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
		<input type="hidden" id="channelId"  name="channelId" value="${channelId}"/>
		<input class="return-button" type="submit" value="返回列表" />
	</form>
	<div class="clear"></div>
</div>

<div  class="body-box">

<form id="newsForm" name="newsForm" method="post" action="<%=basePath%>paper/add">
<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>

<!-- 文章正文区域start -->
<div id="div-content">
<table width="100%"  cellpadding="2" cellspacing="1" border="0">
<tr id="tr-channel">
<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>栏目:</td>
<td colspan="3" width="90%" class="pn-fcontent" >
<div style="float:left">
     <select id="channelId"  name="channelId">
         <c:forEach items="${channels}" var="item">
              <c:choose>
				   <c:when test="${item.id == channelId}">
				          <option value="${item.id }" selected="selected">${item.name }</option>
				   </c:when>
				   <c:otherwise>
				          <option value="${item.id }" >${item.name }</option>
				   </c:otherwise>
				   </c:choose>
           </c:forEach>
      </select>                	       			
</div>
<div class="clear"></div>
</td>
</tr>

<tr id="tr-title">
<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>标题:</td>
<td colspan="3" width="90%" class="pn-fcontent">
<input id="title"  type="text"  name="title" class="required" size="70" maxlength="16"/>
</td>
</tr>
<tr id="tr-type">
<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>文章类型:</td>
<td colspan="1" width="40%" class="pn-fcontent">
<select  id="type" name="type" onchange ="javascript:addOuterPaper(this.options[this.options.selectedIndex].value)">
	<option value="0" selected>内部文章</option>
	<option value="1">外链</option>
</select>
</td>
</tr>
</table>
<div id="div-inpaper">
<table id="table-content" width="100%"  cellpadding="2" cellspacing="1" border="0">
	<tr id="tr-description">
	<td width="10%" class="pn-flabel pn-flabel-h">摘要:</td>
	<td colspan="3" width="90%" class="pn-fcontent">
		<textarea id ="description" cols="70" rows="3" name="description"></textarea>
	</td>
	</tr>
	
	<tr id="tr-author">
	<td width="10%" class="pn-flabel pn-flabel-h">作者:</td><td colspan="1" width="40%" class="pn-fcontent">
	<input id ="author" type="text" maxlength="100" name="author" />
	</td>
	</tr>
	
	<tr id="tr-isTop">
	<td width="10%" class="pn-flabel pn-flabel-h">固顶级别:</td>
	<td colspan="1" width="40%" class="pn-fcontent">
	<select  id="isTop" name="isTop">
		<option value="0">不固顶</option>
		<option value="1">固顶</option>
	</select>
	</td>
	</tr>
	<tr id="tr-pregWeeks">
	<td width="10%" class="pn-flabel pn-flabel-h">推荐孕周:</td>
	<td width="10%" colspan="1" class="pn-fcontent">
	<select  id="pregStage" name="pregStage" onclick="showrecPreg()">
		<option value="0" selected>全孕周</option>
		<option value="1" >40周以上</option>
		<option value="2" >0-40周</option>
	</select>
	<div id = "div_recPreg" style="display:none">
	<input id ="recPregWeeks"  type="text" maxlength="6" name="recPregWeeks" value = "" />
	<span class="pn-fhelp">0-40之间的数字</span>
	</div>
	</td>
	
	</tr>
	<tr id="tr-hospital">
	<td width="10%" class="pn-flabel pn-flabel-h">所属医院:</td><td colspan="1" width="40%" class="pn-fcontent">
	<input id ="hospital" type="text" maxlength="100" name="hospital" />
	</td>
	</tr>
	<tr id="tr-titleImg" >
	 <td width="10%" class="pn-flabel pn-flabel-h"><span class='pn-frequired'>*</span>标题图片:</td>
	 <td colspan="1" width="40%" class="pn-fcontent">
	        <input class="button"  type="button" id="btnUploadFile" value="上传图片" onclick="javascript:uploanFile('btnUploadFile','pic','titleImg','picDelet','paper','')" />
	        <input type="hidden" id="titleImg" name="titleImg" value=""/>
	        <span class="pn-fhelp" id="pic">无图片</span><span id = "picDelet" ></span>
	 </td>
	</tr>
	
	<!-- 版块区域start -->
	<tr>
	<td width="10%"  class="pn-flabel pn-flabel-h">文章正文:</td>
	<td class="pn-fcontent">
		<input type="button" value="添加版块"  onclick="javascript:addNewSection('div-inpaper')" /> &nbsp; 
	</td>
	</tr>
</table>
</div>
<div class="clear"></div>
<div id="div-outpaper" style="display:none">
<table  width="100%"  cellpadding="2" cellspacing="1" border="0">
	<tr id="tr-paperImg" >
		 <td width="10%" class="pn-flabel"><span class='pn-frequired'>*</span>标题图片:</td>
		 <td colspan="1" width="40%" class="pn-fcontent">
		        <input class="button"  type="button" id="obtnUploadFile" value="上传图片" onclick="javascript:uploanFile('obtnUploadFile','opic','otitleImg','opicDelet','paper','')" />
		        <input type="hidden" id="otitleImg" name="otitleImg" value=""/>
		        <span class="pn-fhelp" id="opic">无图片</span><span id = "opicDelet" ></span>
		 </td>
	</tr>
	<tr id="tr-paperurl">
		<td width="10%" class="pn-flabel pn-flabel-h"><span class='pn-frequired'>*</span>链接:</td>
		<td colspan="1" width="40%" class="pn-fcontent">
			<input id ="paperurl" type="text"  name="paperurl" />
		</td>
	</tr>
</table>
</div>
</div>
<!-- 文章正文区域end -->
	
<table width="100%"  cellpadding="2" cellspacing="1" border="0">
	<tr>
	<td colspan="4" class="pn-fbutton">
		<input type="submit" value="提交"  class="submit" onclick= "javascript:callAddNewsAction()"/> &nbsp; 
		<input type="reset" value="重置" class="reset"/>
	</td>
	</tr>
</table>
</form>
</div>

<script type="text/javascript">
	function uploanFile(buttonId,picId,titleImgId,spanId,type,deleId){
		var button = document.getElementById(buttonId); 
		var ajaxUploadImage = new AjaxUpload(button,{
		
			action: '<%=basePath%>upload',
			autoSubmit: true, //交由确定按钮提交
			name: 'filedata',   
			
			onChange:function(file,ext){//当选择文件后执行的方法,ext存在文件后续,可以在这里判断文件格式
				hasAddSpecialImage=true;
			},
				        
			onSubmit : function(file, ext){
			    if (!(ext && /^(jpg|JPG|png|PNG|gif|GIF)$/.test(ext))) {  
			        alert("您上传的图片格式不对，仅能上传jpg、JPG、png、PNG、gif、GIF的图片，请重新选择！");  
			        return false;  
			    }  
			
				 this.disable();
			},
			
			onComplete: function(file, response){ //上传完毕后的操作	
				var start = response.indexOf("{");
				var end = response.indexOf("}");
				var	msg = response.substring((start+1), end);
				
				
				if(msg=="size"){
					alert("文件大于1M");
				}else{
					document.getElementById(picId).innerHTML = "<img height ='50' width = '150' src = '<%=basePath%>" + msg + "'/>";
					document.getElementById(titleImgId).value = msg;
					document.getElementById(spanId).innerHTML="<a href=\"javascript:deleteImg('"+type+"','"+deleId+"','"+picId+"','"+titleImgId+"','"+spanId+"')\">删除</a>";
				}
			
				this.enable();
		}
		});	
	}	
	/* 初始化上传控件 */
	uploanFile('btnUploadFile','pic','titleImg','picDelet','paper','');
	uploanFile('obtnUploadFile','opic','otitleImg','opicDelet','paper','');
</script>	
</body>
</html>