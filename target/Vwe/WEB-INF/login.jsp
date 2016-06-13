<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="head.jsp" %>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>登录</title>
<script src="<%=basePath%>res/thirdparty/md5/md5.js" type="text/javascript"></script>
<script type="text/javascript">
if (window != top) 
	top.location.href = location.href;
	
$(function() {
	
	function trim(str){
		return (str.replace(/(^\s*)/g, "")).replace(/(^\s*)/g, "")
	}
	$("#loginName").focus();
	$("#submitbtn").click(function(){
		var loginName=document.getElementById("loginName").value;
		var password=document.getElementById("password").value;
		
		if(null == loginName || undefined == loginName){
			$("#loginNamemsg").html("用户名不能为空");
			return false;
		}else{
			loginName = trim(loginName);
			if(loginName.length < 1){
				$("#loginNamemsg").html("用户名不能为空");
				return false;
			}
		}
		if(null == password || undefined == password){
			$("#loginNamemsg").html("密码不能为空");
			return false;
		}else{
			password = trim(password);
			if(password.length < 1){
				$("#loginNamemsg").html("密码不能为空");
				return false;
			}
		}
		var par_data = "loginName="+loginName+"&password="+hex_md5(password);
		$.ajax({ 
			 type: "POST", 
			 url: "<%=basePath%>toLogin",  
			 data: par_data, 
			 success: function(message){ 
				if("succ"== message){//正确
					$("#loginform").submit();
				}else if("password" == message ){
					$("#pwdMsg").html("密码不正确");
				}else{
					$("#loginMsg").html("用户名不存在");
				}
		}});
		
	});
	
});
</script>
<style type="text/css">
body{margin:0;padding:0;font-size:12px;background:url(<%=basePath%>/res/tycms/img/login/bg.jpg) top repeat-x;}
.input{width:150px;height:17px;border-top:1px solid #404040;border-left:1px solid #404040;border-right:1px solid #D4D0C8;border-bottom:1px solid #D4D0C8;}
</style>
</head>
<body>
<form id="loginform"  action = "<%=basePath%>admin/index" method="get">
<table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="200">&nbsp;</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="423" height="280" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            </table></td>
          <td width="40" align="center" valign="bottom"><img src="<%=basePath%>res/tycms/img/login/line.jpg" width="23" height="232" /></td>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="90" align="center" valign="bottom"><img src="<%=basePath%>res/tycms/img/login/ltitle.jpg" /></td>
              </tr>
              <tr>
                <td>
                <div>

                </div>
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5">
                	
                    <tr>
                      <td width="91" height="40" align="right"><strong> 用户名：</strong></td>
                      <td width="211"><input type="input" id="loginName" vld="{required:true}" maxlength="100" class="input"/>
                      <span id= "loginMsg"  name= "loginMsg"></span></td>
                    </tr>
                    <tr>
                      <td height="40" align="right"><strong>密码：</strong></td>
                      <td><input type="password" id="password" maxlength="32" vld="{required:true}" class="input"/>
                      <span id= "pwdMsg"  name= "pwdMsg"></span></td>
                    </tr>
                    
                    <tr>
                      <td height="40" colspan="2" align="center">
					    <img name="submmit" id="submitbtn" style="cursor: pointer" src="<%=basePath%>res/tycms/img/login/login.jpg"  />
                        &nbsp; &nbsp; <img name="reg" style="cursor: pointer" src="<%=basePath%>res/tycms/img/login/reset.jpg" onclick="document.forms[0].reset()" /> </td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>

</body>
</html>

