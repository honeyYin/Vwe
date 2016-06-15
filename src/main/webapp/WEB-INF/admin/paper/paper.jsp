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
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
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
			var queryTitle = document.getElementById("queryTitle").value;
			var isDraft = document.getElementById("isDraft").value;
			var type = document.getElementById("type").value;
			var st =  document.getElementById("status").value;
				if(st == '待发布'){
					if(confirm("您确定发布吗？")) {
						var paperId = jvForm.elements["paperId"].value;
						var par_data="hasAudit=true&paperId="+paperId;
						$.ajax({ 
							 type: "GET", 
							 url: "<%=basePath%>paper/updateAudit",  
							 data: par_data, 
							 success: function(message){ 
								 if(message == "succ"){
									 window.parent.frames["rightFrame"].location.href = "<%=basePath%>paper/detail?paperId="+paperId+"&pageNo="+pageNo+"&channelId="+channelId
											 +"&type="+type+"&queryTitle="+queryTitle+"&isDraft="+isDraft;
								 }else if(message == "null"){
									 alert("文章不存在");
								 }else if(message == "draft"){
									 alert("草稿文章不允许发布");
								 }else if(message == "section"){
									 alert("板块标题不能为空");
								 }else{
									 alert("请先完善文章信息");
								 }
						}});
					}
				}else{
					alert("该文件已发布或该文件的状态暂不支持发布!");
				}
			
	}
	//取消发布
	function callUnVerify(){
			var channelId = document.getElementById("channelId").value;
			var pageNo = document.getElementById("pageNo").value;
			var queryTitle = document.getElementById("queryTitle").value;
			var isDraft = document.getElementById("isDraft").value;
			var type = document.getElementById("type").value;
			var st =  document.getElementById("status").value;
				if(st == '已发布'){
					if(confirm("您确定取消发布吗？")) {
						var paperId = jvForm.elements["paperId"].value;
						 window.parent.frames["rightFrame"].location.href = "<%=basePath%>paper/cancleAudit?paperId="+paperId+"&pageNo="+pageNo+"&channelId="+channelId
						 +"&type="+type+"&queryTitle="+queryTitle+"&isDraft="+isDraft+"&redirect=/paper/detail";
					}
				}else{
					alert("该文件的状态暂不支持取消发布!");
				}
			
	}
</script>
<style type="text/css">
.div1{ width:200px; height:0px; border:#999 1px solid; float:left;margin-left:10%;}
.div2{ width:200px; height:0px; border:#999 1px solid; float:left;margin-right:10%;}
.div3{ float:left; height:5px; line-height:5px; margin:0px 10px 0px 10px;font-size:15px}
</style>
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
<div class="body-box" >
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
<div style="border:1px solid #ccc;">
	<h1 align="center">${paper.title}</h1>
	<p class="info">
		发布者: ${paper.author}
		&nbsp;${paper.viewCount}人已读
		<c:if test="${paper.hasAudit == true}">
		&nbsp; 发布时间:  ${paper.auditTime}	
		</c:if>	
	</p>
<div style="margin:0px 20px 0px 20px;">
<p>${paper.description}</p>
		<!-- 版块区域 -->
<c:forEach items="${paper.sections}" var="section">
	<h2 align="center"><B>---------------${section.title}--------------</B></h2>
  	<c:forEach items="${section.paras}" var="para">
  	<div >
		<c:if  test="${para.orderNum !=-1}">
			<div><p style="padding-right:90%"><B>${para.orderNum} ${para.title}</B></p></div>
		</c:if>
		<c:if test="${para.content !=null && para.content !=''}">
			<p style="text-align:left">${para.content }</p>
		</c:if>
		<c:if test="${para.imgUrl !=null && para.imgUrl !=''}">
			<img style="margin-left:10%" src="<%=basePath%>${para.imgUrl}"  width="650" height= "450"/>
		</c:if>
  	</div>
  	</c:forEach>
  	<c:forEach items="${section.outLinks}" var="outLink">
  	<div >
		<div><p style="padding-right:65%;padding-top:20px;font-size:15px;"><B>>${outLink.title}</B></p></div>
		<p style="padding-right:68%;font-size:10px;">${outLink.secTitle }</p>
		<p style="padding-right:78%;font-size:20px;margin-top:10px;"><font color="#FF0000">￥${outLink.prize }</font>
		<div style="float:right;font-size:20px;margin-top:-70px;margin-right:10px"><a  href="${outLink.outerUrl}">详情</a></div>
  	</div>
  	</c:forEach>   
</c:forEach>
</div>
</div>
</div>
</body>
</html>