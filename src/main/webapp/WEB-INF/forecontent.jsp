<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="${paper.description}"/>
<title>${paper.title}</title>
<link rel="stylesheet"  href="<%=basePath%>res/foreground/css/style.css" type="text/css" media="all" />
<style type="text/css">
.div1{ width:70px; height:0px; border:#999 1px solid; float:left;margin-left:15px;}
.div2{ width:70px; height:0px; border:#999 1px solid; float:left;}
.div3{ float:left; height:5px; line-height:5px; margin:0px 10px 0px 10px;font-size:12px}

</style>
</head>
		
<body>
<br /><br />
<div class="content" style="background:url(../res/foreground/images/iphone.png);align:center;height:645px;width:360px;">
<div class="mainNav" id="wins" 
 	style="align:center;height:498px;width:318px;position:relative; top:55px;margin-left:22px;overflow:auto;">
<!-- mainNav -->

		<c:if test="${paper.titleImg !=null && paper.titleImg !=''}">
			<img src="<%=basePath%>${paper.titleImg}"  width="300px" height= "200px" />
		</c:if>
       
<div class="c"></div>

<!--content --> 

<div  align = "center">
	<h2 align="center">${paper.title}</h2>
	<span style="align:center;margin-right:40%">${paper.viewCount}人已读</span><br />
	<p style="text-align:left">${paper.description}</p>
	<br/>
	<div class="c"></div>	
<!--article --> 
	
<div >
<!-- 版块区域 -->
<c:forEach items="${paper.sections}" var="section">
	<div class="div1"></div><div class="div3"><B>${section.title}</B></div><div class="div2"></div><br />
  	<c:forEach items="${section.paras}" var="para">
  	<div style="margin-top:10px">
	  	<c:if  test="${para.orderNum !=-1}">
			<div><p style="margin-right:80%"><B>${para.orderNum} ${para.title}</B></p></div>
		</c:if>
		<c:if test="${para.content !=null && para.content !=''}">
			<p style="text-align:left">${para.content }</p>
		</c:if>
		<c:if test="${para.imgUrl !=null && para.imgUrl !=''}">
			<img src="<%=basePath%>${para.imgUrl}"  width="300px" height= "200px"/>
		</c:if>
  	</div>
  	</c:forEach>
  	<hr />
  	<c:forEach items="${section.outLinks}" var="outLink">
  	<div style="align:left">
		<div><p style="padding-top:5px;margin-right:46%"><B>>${outLink.title}</B></p></div>
		<p style="margin-right:60%">${outLink.secTitle }</p>
		<p style="margin-right:70%;font-size:20px;margin-top:10px;"><font color="#FF0000">￥${outLink.prize }</font>
		<div style="float:right;font-size:25px;margin-top:-50px;margin-right:5px"><a  href="${outLink.outerUrl}">详情</a></div>
		</p>
		
  	</div>
  	</c:forEach>   
</c:forEach>
</div>
<%-- <div style="margin-top:20px">
	<div class="div1"></div><div class="div3"><B>你可能也需要</B></div><div class="div2"></div>
		<c:forEach items ="${rePapers}" var="item">
			<div style="margin-top:10px">
				<p><img style="margin-top:10px;align:left" src="<%=basePath%>${item.titleImg}"  width="150" height= "150"/>
					<B><a href="<%=request.getContextPath()%>/paper/viewPaper?paperId=${item.id}">${item.title}</a></B>
					<span >${item.description }</span>
					</p>
			</div>
	    </c:forEach>
</div> --%>
</div>

<div class="c"></div>
<!--end link --> 
</div>
</div>

</body>
</html>
