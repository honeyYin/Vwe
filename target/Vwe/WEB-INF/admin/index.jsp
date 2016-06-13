<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="commonPath.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>后台管理</title>
</head>
<frameset rows="72,*" frameborder="0" border="0" framespacing="0">
	<frame src="<%=basePath%>admin/top" name="topFrame" noresize="noresize" id="leftFrame" />
	<frame src="<%=basePath%>admin/main" name="mainFrame" id="mainFrame" />
</frameset>
<noframes><body></body></noframes>
</html>