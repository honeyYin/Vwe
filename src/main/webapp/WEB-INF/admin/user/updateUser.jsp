<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
//验证框架，验证密码
var uext = true;
$(function() {
	$("#jvForm").validate();
	
	//验证用户名是否存在
	
	$("#loginName").blur(function(){
		var userId = document.getElementById("userId").value;
		var par_data="loginName="+document.getElementById("loginName").value+"&userId="+userId;
		//验证中。。。
		$("#usstate").html("验证中。。。");
		$.ajax({ 
			 type: "POST", 
			 url: "<%=basePath%>user/checkLoginName",  
			 data: par_data, 
			 success: function(message){ 
				//alert(message);
				if("false"== message){//可用
					$("#usstate").html("可以使用");
					uext = true;
				}else{
					$("#usstate").html("已存在");
				}
		}});
	});
	
	
});

//判断用户名、密码、真是姓名是否为空
function callAddUserAction(){
	var username=document.getElementById("loginName").value;
	var realname=document.getElementById("realName").value;
	if(username==""){
		alert("[用户名]不能为空");
		return false;
	}else if(!uext){
		alert("用户名已经被使用，请重新输入！");
	}else if(realname==""){
		alert("[真实姓名]不能为空");
		return;
	}else{
			document.jvForm.action ="<%=basePath%>/user/edit";
			document.jvForm.submit();
	}
}



</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 用户管理 - 修改</div>
	<form class="ropt" action="<%=basePath%>user/list">
		<input type="submit" value="返回列表"  class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form method="post"  id="jvForm" name="jvForm" >
<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
<tr>
<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>用户名:</td>
<td colspan="3" width="38%" class="pn-fcontent">
	<input type="hidden" id="userId" name="userId" value="${user.id}" />
	<input type="text" maxlength="100" id="loginName" name="loginName" maxlength="100" value="${user.loginName }" />
	<span id="usstate"></span>
</td>
</tr>
<tr>
<td width="12%" class="pn-flabel pn-flabel-h">电子邮箱:</td>
<td colspan="3" width="38%" class="pn-fcontent">
	<input type="text" name="email" class="email" value="${user.email }" size="30"/>
</td>
</tr>
<tr>
<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>真实姓名:</td>
<td colspan="3" width="38%" class="pn-fcontent">
	<input type="text" maxlength="100" id="realName" name="realName" value="${user.realName }"/>
</td>
</tr>
<tr id="tr-isTop">
<td width="10%" class="pn-flabel pn-flabel-h">级别:</td><td colspan="1" width="40%" class="pn-fcontent">
<c:choose>
	<c:when test="${user.isAdmin==1}">
		<select  id="isAdmin" name="isAdmin">
			<option id="isAdmin0" value="1" selected>管理员</option>
			<option id="isAdmin1" value="0">普通人员</option>
		</select> 
	</c:when>
	<c:otherwise>
		<select  id="isAdmin" name="isAdmin">
			<option id="isAdmin0" value="1">管理员</option>
			<option id="isAdmin1" value="0" selected>普通人员</option>
		</select>   
	</c:otherwise>
</c:choose>
</td>
</tr>
<tr>
<td colspan="4" class="pn-fbutton">
	<input type="button" value="提交" class="submit"  onclick="callAddUserAction()"/> &nbsp; 
	<input type="reset" value="重置"  class="reset"/>
</td>
</tr>
</table>
</form>
</div>
</body>
</html>