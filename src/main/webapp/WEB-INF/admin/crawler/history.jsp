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
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 一键抓取</div>
	<div class="clear"></div>
</div>

<div class="body-box">
	<form name="form1" id ="form1"  action="<%=basePath%>crawler/queryByCondition" method ="get" padding-top:5px;">
	<div>
		<select name="siteId" >
			<option value="0" selected="selected">--请选择抓取的目标网站--</option>
			<c:forEach items="${sites}" var="v_site">
				<option value="${v_site.id }" >${v_site.title }</option>
			</c:forEach>
		</select>
	</div>
	<div>
		URL: <input type="text" id="url"  name="url" value="${url}" style="width:100px"/>
		关键词: <input type="text" id="queryTitle"  name="queryTitle" value="${queryTitle}" style="width:100px"/>
		<input class="query" type="submit" value="搜索" />
	</div>
	</form>
    <form id="tableForm" name="listform">
        <table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
            <thead class="pn-lthead">
            	<tr>
	                <th>序号</th>
	                <th>名称</th>
	                <th>链接</th>
                </tr>
            </thead>
            <tbody  class="pn-ltbody">
           <c:forEach var="item" items="${links}">
	            <tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	                <td>
	                	${item.order }
	                </td>
	                <td>${item.title }</td>
	                <td align="center">
	                	<input type="text" name="url" value="${item.url }" style="width:40px; border:1px solid #7e9db9"/>
	                </td>
	            </tr>
            </c:forEach>
           </tbody>
        </table>
    </form>
</div>
</body>
</html>