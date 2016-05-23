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
<meta name="description" content="${paper.description}"/>
<title>${paper.title}</title>
<link rel="stylesheet"  href="<%=basePath%>res/foreground/css/style.css" type="text/css" media="all" />
</head>
		
<body>
<br /><br />

<div class="content">

<!-- mainNav -->
<img src="<%=basePath%>${paper.titleImg}"  width="850" height= "450"/>
    <div class="mainNav">
       
    </div>
<div class="c"></div>


<div >

</div> 

<!--content --> 

<div class="detail" align = "center">
	<h1 align="center">${paper.title}</h1>

	<span align="center">阅读人数:${paper.viewCount}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp发表时间：${paper.auditTime}</span>
	
	<div class="c"></div>	
<!--article --> 
	
	<div id="article">
	<p align="left">${paper.description}</p>
	<br/>
<div>
		<!-- 版块区域 -->
<c:forEach items="${paper.sections}" var="section">
	<h1>---------${section.title}-----------</h1>
  	<c:forEach items="${section.paras}" var="para">
  	<div >
		<h1>---------${para.title}-----------</h1>
		<p>${para.content }</p>
		<img src="<%=basePath%>${para.imgUrl}"  width="850" height= "450"/>
  	</div>
  	</c:forEach>
  	<c:forEach items="${section.outLinks}" var="outLink">
  	<div >
		<h1>---------${outLink.title}-----------</h1><br />
		<label >${outLink.secTitle }</label>
		<label >人民币${outLink.prize }元</label>
		<a src="${outLink.outerUrl}">详情</a>
  	</div>
  	</c:forEach>   
</c:forEach>
</div>
	</div>
	
	<ul id="news_nav">
		<c:forEach items ="${rePapers}" var="v_repaper">
	        <li><a href="<%=request.getContextPath()%>/paper/viewPaper?paperId=${v_repaper.id}">${v_repaper.title}</a> </li>
	    </c:forEach>
	</ul>
</div>

<div class="c"></div>
<!--end link --> 

</div>

</body>
</html>
