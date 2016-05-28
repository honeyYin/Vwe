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
<script src="<%=basePath%>res/thirdparty/md5/md5.js" type="text/javascript"></script>
	<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
	
	<script>
	
	//判断用户名、密码、真是姓名是否为空
	function callUpdateUserAction(){
		var userId =document.getElementById("userId").value;
		 var oldPwd=document.getElementById("oldPwd").value; 
		var newPwd=document.getElementById("newPwd").value;
		var snewPwd=document.getElementById("snewPwd").value;
		 if(oldPwd==""){
			alert("[旧密码]不能为空");
			return false;
		}else if(newPwd=="" || snewPwd=="" ){
			alert("[新密码]不能为空");
			return;
		}else if(newPwd != snewPwd){
			alert("[新密码]不一致");
			return;
		}else{
			oldPwd = hex_md5(oldPwd);
			var par_data="oldPwd="+oldPwd+"&userId="+userId;
			$.ajax({ 
				 type: "POST", 
				 url: "<%=basePath%>/user/checkPwd",  
				 data: par_data, 
				 success: function(message){ 
					//alert(message);
					if("false"== message){
						$("#usstate").html("旧密码不正确");
						uext = true;
					}else{
						document.getElementById("newPwd").value=hex_md5(document.getElementById("newPwd").value);
						document.jvForm.submit();
					}
			}});
			 
		}
	}
	
	</script>
	</head>
<body>
	<div class="box-positon">
		<div class="rpos">当前位置: 修改密码</div>
		<form class="ropt" action="<%=basePath%>user/list">
			<input type="submit" value="返回列表"  class="return-button"/>
		</form>
		<div class="clear"></div>
	</div>
	<div class="body-box">
		<form method="post"  id="jvForm" name="jvForm" action="<%=basePath%>user/resetPwd" method="post">
			
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>用户名:</td>
				<td colspan="3" width="38%" class="pn-fcontent">
					<input type="hidden" id="userId" name="userId" value="${userId}"/>
					<input type="text" maxlength="100" id="loginName" name="loginName" disabled="disabled" maxlength="100" value="${loginName}" disabled/>
				</td>
			</tr>
			<tr>
				<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>旧密码:</td>
				<td colspan="3" width="38%" class="pn-fcontent">
					<input type="password" maxlength="100" id="oldPwd" name="oldPwd" value=""/>
					<span id="usstate"></span>
				</td>
			</tr>
			<tr>
				<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>新密码:</td>
				<td colspan="3" width="38%" class="pn-fcontent">
					<input type="password" maxlength="100" id="newPwd" name="newPwd" value=""/>
				</td>
			</tr>
			<tr>
				<td width="12%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>新密码:</td>
				<td colspan="3" width="38%" class="pn-fcontent">
					<input type="password" maxlength="100" id="snewPwd" name="snewPwd" value=""/>
				</td>
			</tr>
			<tr>
			<td colspan="4" class="pn-fbutton">
				<input type="button" value="提交" class="button" class="submit" onclick="callUpdateUserAction()"/> &nbsp; 
				<input type="reset" value="重置" class="reset" class="reset"/>
			</td>
			</tr>
			</table>
		</form>
	</div>
</body>
</html>