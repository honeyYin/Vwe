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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
h1{text-align:center;font-size:18px;}
.info{text-align:center;}
</style>

<script type="text/javascript">
	//发布
	function callVerify(){
			var channelId = document.getElementById("channelId").value;
			var pageNo = document.getElementById("pageNo").value;
			var st =  document.getElementById("status").value;
				if(st == '待发布'){
					if(confirm("您确定发布吗？")) {
						var paperId = jvForm.elements["paperId"].value;
						window.location.href = "<%=basePath%>paper/updateAudit?redirect=/paper/detail&hasAudit=true&paperId="+paperId+"&channelId="+channelId+"&pageNo="+pageNo; 
					}
				}else{
					alert("该文件已发布或该文件的状态暂不支持发布!");
				}
			
	}
	//取消发布
	function callUnVerify(){
			var channelId = document.getElementById("channelId").value;
			var pageNo = document.getElementById("pageNo").value;
			var st =  document.getElementById("status").value;
				if(st == '已发布'){
					if(confirm("您确定取消发布吗？")) {
						var paperId = jvForm.elements["paperId"].value;
						window.location.href = "<%=basePath%>paper/updateAudit?redirect=/paper/detail&hasAudit=false&paperId="+paperId+"&channelId="+channelId+"&pageNo="+pageNo;
					}
				}else{
					alert("该文件的状态暂不支持取消发布!");
				}
			
	}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置:  内容管理 -- 查看</div>
	<form class="ropt" action="<%=basePath%>paper/list" method="get">
		<input type="hidden" id="queryTitle"  name="queryTitle" value="${queryTitle}" />
		<input type="hidden" id="type"  name="type" value="${type}" />
		<input type="hidden" id="isDraft"  name="isDraft" value="${isDraft}" />
		<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
		<input type="hidden" id="channelId"  name="channelId" value="${channelId}"/>
		<input class="return-button" type="submit" value="返回列表" />
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form id="jvForm" method="get" style="padding-top:5px" action="<%=basePath%>paper/toEdit">
	<input type="hidden"  id="paperId" name="paperId" value="${paper.id}"/>
	<input type="hidden" id="channelId"  name="channelId" value="${channelId}"/>
	<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
	<c:choose>
		<c:when test="${paper.hasAudit == true}">
			<input type="hidden" id="status" name="status" value="已发布"/>
			<input class="front-view" type="button" value="取消发布" onclick="callUnVerify()"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" id="status" name="status" value="待发布"/>
			<input class="check" type="button" value="发布" onclick="callVerify()"/>
		</c:otherwise>
	</c:choose>
	<input class="edit" type="submit" value="修改" style="color:gray"/>
	<input class="front-view" type="button" value="前台预览" onclick="window.open('<%=basePath%>paper/viewPaper?paperId=${paper.id}&channelId=${channelId}&pageNo=${pageNo}')"/>
</form>
<form id="viewInFront"  target="_blank" method="get"></form>
<div style="border:1px solid #ccc;">
	<h1 align="center">${paper.title}</h1>
	<p class="info">
		发布者: ${paper.author}
		&nbsp;${paper.viewCount}人已读
		<c:if test="${paper.hasAudit == true}">
		&nbsp; 发布时间:  ${paper.auditTime}	
		</c:if>	
	</p>
<div>
<p>&nbsp;&nbsp;${paper.description}</p>
		<!-- 版块区域 -->
<c:forEach items="${paper.sections}" var="section">
	<h1>---------${section.title}-----------</h1>
  	<c:forEach items="${section.paras}" var="para">
  	<div >
		<h1>---------${para.title}-----------</h1>
		<p>${para.content }</p>
		<c:if test="${para.imgUrl !=null && para.imgUrl !=''}">
			<img src="<%=basePath%>${para.imgUrl}"  width="850" height= "450"/>
		</c:if>
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
</div>
</body>
</html>