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
    
    <title>My JSP 'modify.jsp' starting page</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 栏目管理 - 列表</div>
	<form class="ropt" action="<%=basePath%>channel/toAdd" method="get">
            <input type="submit" value="添加" class="add" style="margin-right:20px"/>
        </form>
	<div class="clear"></div>
</div>
<div class="body-box">
    <form id="tableForm" method="post" name="listform">
        <table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
            <thead class="pn-lthead">
            	<tr>
	                <th width="20"></th>
	                <th>ID</th>
	                <th>栏目名称</th>
	                <th>排列顺序</th>
	                <th>操作选项</th>
                </tr>
            </thead>
            <tbody  class="pn-ltbody">
           
           <c:forEach var="item" items="${channels}">
	            <tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	                <td>
	                	<input type='checkbox' name='ids' value='${item.id }'/>
	                </td>
	                <td>
	                	${item.id }
	                	<input type="hidden" name="wids" value="${item.id }"/>
	                </td>
	                <td>${item.name }</td>
	                <td align="center">
	                	<input type="text" name="priority" value="${item.priority }" style="width:40px; border:1px solid #7e9db9"/>
	                </td>
	                <td align="center">		
	                	<a href="<%=basePath%>channel/toEdit?channelId=${item.id }" class="pn-opt">修改</a> | 
	                	<a href="<%=basePath%>channel/delete?channelId=${item.id }" class="pn-opt" onclick="return confirm('确定要删除？');">删除</a>
	                </td>
	            </tr>
            </c:forEach>
           </tbody>
        </table>
        <div style="margin-top:15px;">
            <input type="button" value="保存排列顺序"  onclick="optPriority()" class="save-order"/>
        </div>
    </form>
</div>
<script type="text/javascript">
	function optPriority(){
		document.forms["listform"].action = "<%=basePath%>channel/saveOrder";
		document.forms["listform"].submit();
	}

</script>
</body>
</html>