<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page  import="com.entity.Paper"%>
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
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/paperCommon.js"></script>

<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
<style>
	.pages {
	text-align: right;
	padding: 5px;
	line-height: 20px;
	margin-top: 10px;
	}
	.pages a, .pages a:visited {
	color: #999;
	padding-left: 8px;
	padding-right: 8px;
	text-decoration: none;
	font-family: Arial, Helvetica, sans-serif;
	}
	.pages a:hover {
	color: #036;
	}

</style>
<script >

//jquery批量删除
function jqcallDelBatch(){  //jquery获取复选框值
		var chk_value =[];
		$('input[name="ids"]:checked').each(function(){
			chk_value.push($(this).val());
		});
		if(chk_value.length==0){
			alert("请选择您要操作的数据");
		}else{
				if(confirm("您确定删除吗？")) {
				document.tableForm.action = "<%=basePath%>paper/batchDelete";   
				document.tableForm.submit();
		 	}
		 
		}
}
//jquery批量删除
function jqcallVerify(){  //jquery获取复选框值
		var chk_value =[];
		$('input[name="ids"]:checked').each(function(){
			chk_value.push($(this).val());
		});
		if(chk_value.length==0){
			alert("请选择您要操作的数据");
		}else{
				if(confirm("您确定发布吗？")) {
				document.tableForm.action = "<%=basePath%>paper/batchAudit";   
				document.tableForm.submit();
		 	}
		 
		}
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 文章管理  -- 列表</div>
	<form class="ropt" name="inform" action="<%=basePath%>paper/toAdd" method="get">
		<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}"/>
		<input type="hidden" id="channelId" name="channelId" value="${channelId}"/>
		<input class="add" type="submit" value="添加" />
	</form>
	<div class="clear"></div>
</div>


<div class="body-box">
<!-- action="queryByCondition.do" -->
<form name="form1" id ="form1"  action="<%=basePath%>paper/queryByCondition" method ="get" padding-top:5px;">
<div>
<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}"/>
标题: <input type="text" id="queryTitle"  name="queryTitle" value="${queryTitle}" style="width:100px"/>
<select name="channelId">
	<option value="0" selected="selected">--所有栏目--</option>
	<c:forEach items="${channels}" var="v_channel">
	<c:choose>	
		<c:when test="${v_channel.id == channelId}">
			<option value="${v_channel.id }" selected="selected">${v_channel.name }</option>
		</c:when>
		<c:otherwise>
			<option value="${v_channel.id }" >${v_channel.name }</option>
		</c:otherwise>
	 </c:choose>
	</c:forEach>
</select>
<input class="query" type="submit" value="搜索" />
</div>
</form>
<form id="tableForm" name="tableForm" method="post">
<input type="hidden" id="queryTitle"  name="queryTitle" value="${queryTitle}" />
<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
<thead class="pn-lthead">
<tr>
	<th width="20"><input type='checkbox' id="allids" onclick="jqselectCheck()"/></th>
	<th>ID</th>
	<th>栏目</th>
	<th>标题</th>
	<th>作者</th>
	<th>点击量</th>
	<th>状态</th>
	<th>发布时间</th>
	<th>操作选项</th>
</tr>
</thead>
<tbody  class="pn-ltbody">

<c:forEach items="${papers}" var="item"> 
<tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	<td><input type="checkbox" id="ids" name="ids" value="${item.id}"/></td>
	<td>${item.id}</td>
	<td>
		<c:choose>
			<c:when test="${item.isTop ==0}">
				
			</c:when>
			<c:otherwise>
				<label style="color:red"><strong>[固顶]</strong></label>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${item.isRecom ==0}">
				
			</c:when>
			<c:otherwise>
				<label style="color:red"><strong>[荐]</strong></label>
			</c:otherwise>
		</c:choose>
		<strong>[${item.channelName}]</strong>
	</td>
	<td align="center">${item.title}</td>
	<td align="center">${item.author}</td>
	<td align="right">${item.viewCount}</td>
	<c:choose>
		<c:when test="${item.hasAudit == true}">
			<td align="center">已发布</td>
			<td align="center">${item.auditTime}</td>
		</c:when>
		<c:otherwise>
			<td align="center">待发布</td>
			<td align="center"></td>
		</c:otherwise>
	</c:choose>
	
	<td align="center">
		<a href="<%=basePath%>paper/detail?paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">查看</a>
		 | <a href="<%=basePath%>paper/toEdit?paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">修改</a> 
		 | <a href="<%=basePath%>paper/delete?paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt" onclick="return confirm('您确定要删除吗？');">删除</a>
		 | 
		<c:choose>
			<c:when test="${item.hasAudit == true}">
				<a id ="${item.id}" href="<%=basePath%>paper/updateAudit?hasAudit=false&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">取消发布</a>
			</c:when>
			<c:otherwise>
				<a id ="${item.id}" href="<%=basePath%>paper/updateAudit?hasAudit=true&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">发布</a>
			</c:otherwise>
		</c:choose>
		 | 
		<c:choose>
			<c:when test="${item.isTop == 1}">
				<a id ="${item.id}" href="<%=basePath%>paper/updateTop?isTop=0&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">取消置顶</a>
			</c:when>
			<c:otherwise>
				<a id ="${item.id}" href="<%=basePath%>paper/updateTop?isTop=1&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}&queryTitle=${queryTitle}" class="pn-opt">置顶</a>
			</c:otherwise>
		</c:choose>
		 | 
		<c:choose>
			<c:when test="${item.isRecom == 1}">
				<a id ="${item.id}" href="<%=basePath%>paper/updateRecom?isRecom=0&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}" class="pn-opt">取消推荐</a>
			</c:when>
			<c:otherwise>
				<a id ="${item.id}" href="<%=basePath%>paper/updateRecom?isRecom=1&paperId=${item.id}&channelId=${channelId}&pageNo=${pageNo}" class="pn-opt">推荐</a>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</c:forEach> 
</tbody>
</table>

<div style="margin-top:15px;">
	<c:choose>
		<c:when test="${list[0].afterCheck==1}">
		
		</c:when>
		<c:otherwise>
			<input type="button" value="删除"  onclick ="jqcallDelBatch()" class="del-button"/>
		</c:otherwise>
	</c:choose>
	<input type="button" value="发布"  onclick ="jqcallVerify()" class="check"/>
</div>
<div class="pages">
	<script>
			function toPage(){
				var channelId = document.getElementById("channelId").value;
				var queryTitle = document.getElementById("queryTitle").value;
				var current = document.getElementById("topageNo").value;
				var last = document.getElementById("maxPageNo").value;
				if(isNaN(current)||!current||parseInt(current)!=current||current>last||current<1){
					alert("页数：非法输入！");
				}else{
					if(current<1){
						current=1;
					}
					if(current>last){
						current=last;
					}
					var path = "<%=request.getContextPath()%>/paper/queryByCondition?queryTitle="+queryTitle+"&pageNo="+current+"&channelId="+channelId;
					location.href=path;
				}
			}
		 	
	</script>
		<input type="hidden" id="channelId" name="channelId" value="${channelId}"/>
		<input type = "hidden" id="pageNo" name="pageNo" value="${pageNo}"/> 
		<input type = "hidden" id="maxPageNo" name="maxPageNo" value="${maxPageNo}"/> 
		共  ${maxPageNo} 页 
		<input type="text" id="topageNo" name="topageNo" style="width:25px" value="${pageNo}"/>
		<a href="#" onclick="toPage()">跳转</a>
		<a href="<%=request.getContextPath()%>/paper/queryByCondition?queryTitle=${queryTitle}&pageNo=1&channelId=${channelId}">首页</a>
		<c:choose>
		<c:when test="${pageNo <= 1}">
			<a href="<%=request.getContextPath()%>/paper/queryByCondition?queryTitle=${queryTitle}&pageNo=1&channelId=${channelId}">上一页</a>
		</c:when>
		<c:otherwise>
			<a href="<%=request.getContextPath()%>/paper/queryByCondition?queryTitle=${queryTitle}&pageNo=${pageNo-1}&channelId=${channelId}">上一页</a>
		</c:otherwise>
		</c:choose> 
		<c:choose>
		<c:when test="${hasNext == true}">
			<a href="<%=request.getContextPath()%>/paper/queryByCondition?queryTitle=${queryTitle}&pageNo=${pageNo+1}&channelId=${channelId}">下一页</a>
			<a href="<%=request.getContextPath()%>/paper/queryByCondition?queryTitle=${queryTitle}&pageNo=${maxPageNo}&channelId=${channelId}">尾页</a>
		</c:when>
		<c:otherwise>
		</c:otherwise>
		</c:choose> 
		
</div>
</form>
</div>

</body>
</html>