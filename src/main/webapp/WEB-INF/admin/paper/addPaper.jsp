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
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/thirdparty/ueditor/themes/default/ueditor.css" />
<script type="text/javascript" src="<%=basePath%>res/thirdparty/ueditor/editor_config.js"></script>
<script type="text/javascript" src="<%=basePath%>res/thirdparty/ueditor/editor_all.js"></script>	
<script type="text/javascript" src="<%=basePath%>res/tycms/js/addNews.js"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/AjaxUpload.js"></script>
<!-- 富文本编辑器 -->
<link rel="stylesheet" href="<%=basePath%>res/thirdparty/kindeditor/themes/default/default.css"/>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script>
KindEditor.ready(function(K) {

	var editor1 = K.create('#content', {

	cssPath : '<%=basePath%>res/thirdparty/kindeditor/plugins/code/prettify.css',

	uploadJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/upload_json.jsp',

	fileManagerJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/file_manager_json.jsp',

	allowFileManager : true
	})

});
function callAddNewsAction(){
	var title = $('#title')[0].value;
	if(title!=null && title!=""){
		$("#newsForm")[0].action="<%=basePath%>paper/add";
		$("#newsForm")[0].submit();
	}else{
		alert("标题不能为空");
	}
} 
</script>

</head>

<body>
<div class="box-positon">
	<div class="rpos"><br />当前位置: 文章管理 -- 添加</div>
	<form class="ropt" action="<%=basePath%>paper/list" method="get">
		<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
		<input type="hidden" id="channelId"  name="channelId" value="${channelId}"/>
		<input class="return-button" type="submit" value="返回列表" />
	</form>
	<div class="clear"></div>
</div>

<div  class="body-box">

<form id="newsForm" name="newsForm" method="post" action="javascript:callAddNewsAction()">
<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
<table width="100%"  cellpadding="2" cellspacing="1" border="0">
<tr id="tr-channel">
<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>栏目:</td>
<td colspan="3" width="90%" class="pn-fcontent" >
<div style="float:left">
<div>
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
<div id="channelsContainer"></div>
</div>
<div class="clear"></div>
</td>
</tr>

<tr id="tr-title">
<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>标题:</td>
<td colspan="3" width="90%" class="pn-fcontent">
<input id="title"  type="text" maxlength="150" name="title" class="required" size="70" maxlength="150"/>
</td>
</tr>

<tr id="tr-secTitle">
<td width="10%" class="pn-flabel pn-flabel-h">副标题:</td>
<td colspan="1" width="40%" class="pn-fcontent">
	<input id="secTitle"  type="text"  name="secTitle" size="35" maxlength="150" class="pn-fruler"/>
</td>
</tr>

<tr id="tr-description">
<td width="10%" class="pn-flabel pn-flabel-h">摘要:</td>
<td colspan="3" width="90%" class="pn-fcontent">
	<textarea id ="description" cols="70" rows="3" name="description"  maxlength="255"></textarea>
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
<td width="10%" class="pn-flabel pn-flabel-h">推荐孕周:</td><td colspan="1" width="40%" class="pn-fcontent">
<input id ="recPregWeeks" type="text" maxlength="20" name="recPregWeeks" />
<span>(限数字)</span>
</td>
</tr>
<tr id="tr-hospital">
<td width="10%" class="pn-flabel pn-flabel-h">所属医院:</td><td colspan="1" width="40%" class="pn-fcontent">
<input id ="hospital" type="text" maxlength="100" name="hospital" />
</td>
</tr>
<tr id="tr-titleImg" >
 <td width="10%" class="pn-flabel">标题图片:</td>
 <td colspan="1" width="40%" class="pn-fcontent">
        <input class="button"  type="button" id="btnUploadFile" value="上传图片" />
        <input type="hidden" id="titleImg" name="titleImg" value=""/>
        <span class="pn-fhelp" id="pic">无图片</span>
 </td>
</tr>

<tr id="tr-txt">
<td width="10%" class="pn-flabel pn-flabel-h">内容:</td>
<td colspan="3" width="90%" class="pn-fcontent">
	<textarea  id="content" name="content" cols="70" rows="10"></textarea>
</td>
</tr>

<tr>
<td colspan="4" class="pn-fbutton">
	<input type="submit" value="提交"  class="submit" /> &nbsp; 
	<input type="reset" value="重置" class="reset"/>
</td></tr>

</table>
</form>
</div>

<script type="text/javascript">
	function cutpres(msg)
	{
		var start = msg.indexOf("{");
		return msg.substr(start).replace("</pre>", "");
	}
	var button = document.getElementById("btnUploadFile"); 
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
			console.log(response);
			var start = response.indexOf("{");
			var end = response.indexOf("}");
			var	msg = response.substring((start+1), end);
			
			
			if(msg=="size"){
				alert("文件大于1M");
			}else{
				document.getElementById("pic").innerHTML = "<img height ='50' width = '150' src = '<%=basePath%>" + msg + "'/>";
				document.getElementById("titleImg").value = msg;
			}
		
			this.enable();
	}
	});	
</script>	
</body>
</html>