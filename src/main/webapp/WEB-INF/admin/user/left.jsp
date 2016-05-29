<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../head.jsp" %>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户-left</title>

</head>
<body class="lbody">
<div class="left">
<%@include file="../date.jsp" %>
<div class="fresh">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
		 <tr>
		 	<td height="35" align="center"><a href="<%=basePath%>user/toResetPwd?userId=${userId}" target="rightFrame">修改密码</a></td>
		 </tr>
		 <tr>
			 <c:if test="${isAdmin == 1}">
			 	<td height="35" align="center"><a href="<%=basePath%>user/list" target="rightFrame">用户管理</a></td>
			 </c:if>
		 </tr>
		 </table>
</div>
</body>
</html>