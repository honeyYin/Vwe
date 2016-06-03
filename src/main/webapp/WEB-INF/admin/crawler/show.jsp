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
	function changeUrl(){
		var id = document.getElementById("siteId").value;
		var url = document.getElementById("urlValue"+id).value;
		document.getElementById("url").value = url;
	}
</script>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 一键抓取</div>
	<div class="clear"></div>
</div>

<div class="body-box">
	<div style="margin-top:15px;line-height:34px;line-height:34px;font-size:12px;" >
		<form name="form1" id ="form1"  action="<%=basePath%>crawler/queryByCondition" method ="get" padding-top:5px;">
		<div >
			&nbsp;目标站点: 
			<select id="siteId" name="siteId" style="height:25px;width:185px" onchange="javascript:changeUrl()">
				<option value="0" selected="selected">--请选择抓取的目标网站--</option>
				<c:forEach items="${sites}" var="v_site">
				<c:choose>
					<c:when test="${v_site.url == url}">
						<option value="${v_site.id }"  selected>${v_site.title }</option>
					</c:when>
					<c:otherwise>
						<option value="${v_site.id }" >${v_site.title }</option>
					</c:otherwise>
				</c:choose>
					
				</c:forEach>
			</select>
			<input type="text" id="url"  name="url" value="${url}" size="75" style="margin-left:22px;height:25px;"/>
		</div>
		<div >
			&nbsp;&nbsp;关键词: <input type="text" id="queryTitle"  name="queryTitle" value="${queryTitle}"  style="height:25px;width:185px"/>
			<input class="query" type="submit" value="搜索" style="margin-left:22px;"/>
		</div>
		<div>
			<c:forEach items="${sites}" var="v_site">
				<input type="hidden" id="urlValue${v_site.id }" name="urlValue${v_site.id }" value="${v_site.url}"/>
			</c:forEach>
		</div>
		</form>
	</div>
    <form id="tableForm" name="listform">
        <table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
            <thead class="pn-lthead">
            	<tr>
	                <th>序号</th>
	                <th>名称</th>
	                <th>链接</th>
	                <th>操作选项</th>
                </tr>
            </thead>
            <tbody  class="pn-ltbody">
           <c:forEach var="item" items="${links}">
	            <tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	                <td align="center" width="10%">
	                	${item.order }
	                </td>
	                <td width="30%">
	                	<a style="width:200px" onclick="window.open('${item.url }')">${item.title }</a>
	                </td>
	                <td align="left" width="40%">
	                	<a style="width:200px" onclick="window.open('${item.url }')">${item.trimUrl }</a>
	                </td>
	                <td align="center" width="20%">		
	                	<a class="pn-opt">入库</a>
	                </td>
	            </tr>
            </c:forEach>
           </tbody>
        </table>
    </form>
</div>
</body>
</html>