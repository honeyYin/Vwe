<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${channelName}</title>
<link rel="stylesheet"  href="<%=basePath%>res/foreground/css/style.css" type="text/css" media="all" />

</head>
		
<body>
<!-- mainNav -->

<div class="content">

	<img src="<%=basePath%>${channelTitleImg}"  width="850" height= "450"/>
    <div class="c"></div>
<!-- news_top  -->
<div id="news_top">
 <p class="position">
 </p>
        
</div>
<!--END news_top  -->


<!--   list -->
<div style="display:inline" >
<div id="news_list_left">
	<p>${channelName}</p>
</div> 
<div id="news_list_right">
    <ul>
		 <c:forEach items="${papers}" var="item">
    			<li><span>${item.auditTime}</span>
    			<a target="_blank"  href="<%=request.getContextPath()%>/paper/viewPaper?paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}">
    			${item.title}
    			<c:choose>
					<c:when test="${item.isRecom ==1}">
						<label style="color:red"><strong>[推荐]</strong></label>
					</c:when>
				</c:choose></a> </li>
    		  </c:forEach>	 
    </ul>

	<div class="pages">
			共  ${maxPageNo} 页 
			<a href="<%=request.getContextPath()%>/paper/viewPaperList?pageNo=1&channelId=${channelId}">首页</a>
			<c:choose>
			<c:when test="${pageNo <= 1}">
				<a href="<%=request.getContextPath()%>/paper/viewPaperList?pageNo=1&channelId=${channelId}">上一页</a>
			</c:when>
			<c:otherwise>
				<a href="<%=request.getContextPath()%>/paper/viewPaperList?pageNo=${pageNo-1}&channelId=${channelId}">上一页</a>
			</c:otherwise>
			</c:choose> 
			<c:choose>
			<c:when test="${hasNext == false}">
			</c:when>
			<c:otherwise>
				<a href="<%=request.getContextPath()%>/paper/viewPaperList?pageNo=${pageNo+1}&channelId=${channelId}">下一页</a>
				<a href="<%=request.getContextPath()%>/paper/viewPaperList?pageNo=${maxPageNo}&channelId=${channelId}">尾页</a>
			</c:otherwise>
			</c:choose> 
			
	</div>
</div>
</div>

<!--link --> 


<div class="c"></div>

<div class="bottom">

<p id="bottom_top" align="center">
主办单位：XXXXXXXX
</p>
<p align="center">
版权所有：XXXXXXXX 2008-2013
</p>
<p align="center">
备案号：XXXXXXXXXX
</p>
</div>

</div>

</body>
</html>
