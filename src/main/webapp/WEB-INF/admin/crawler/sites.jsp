<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>一键抓取</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>

</head>
<script>
</script>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 一键抓取</div>
	<form class="ropt" action="<%=basePath%>crawler/toAddSite" method="get">
            <input type="submit" value="添加站点" class="add"/>
    </form>
	<div class="clear"></div>
</div>

<div class="body-box">
    <form id="tableForm" name="listform">
        <table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
            <thead class="pn-lthead">
            	<tr>
	                <th>ID</th>
	                <th>名称</th>
	                <th>链接</th>
	                <th>操作选项</th>
                </tr>
            </thead>
            <tbody  class="pn-ltbody">
           <c:forEach var="item" items="${sites}">
	            <tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	                <td>
	                	${item.id }
	                </td>
	                <td>${item.title }</td>
	                <td align="center">
	                	<input type="text" name="url" value="${item.url }" style="width:40px; border:1px solid #7e9db9"/>
	                </td>
	                <td align="center">		
	                	<a href="<%=basePath %>crawler/deleteSite?siteId=${item.id}" class="pn-opt">删除</a>
	                </td>
	            </tr>
            </c:forEach>
           </tbody>
        </table>
    </form>
</div>
</body>
</html>