<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
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

<script type="text/javascript">
	//jquery选中所有checkbox
	function jqselectCheck(){  //jquery获取复选框值
 		 if (document.getElementById("allids").checked) {  
                    $("input[name='ids']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox   
                                $(this).attr("checked", true);  
                            })  
                } else {   //反之 取消全选    
                    $("input[name='ids']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox   
                                $(this).attr("checked", false);  
                            })  
                }  

	}
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
					/* document.tableForm.action = "/Vwe/user/batchDelete"; */   
					document.tableForm.submit();
			 }
 		}
	}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 用户管理 - 列表</div>
	<form class="ropt" action="<%=basePath%>user/toAdd" method="get">
		<input class="add" type="submit" value="添加" />
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form id="tableForm"  name="tableForm" action ="<%=basePath%>user/batchDelete" method="post">
<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
<thead class="pn-lthead"><tr>
	<th width="20"><input type='checkbox' id="allids" name="allids" onclick="jqselectCheck()"/></th>
	<th>ID</th>
	<th>登录名</th>
	<th>真实姓名</th>
	<th>电子邮箱</th>
	<th>是否管理员</th>
	<th>状态</th>
	<th>操作选项</th></tr></thead>
<tbody  class="pn-ltbody">
<c:forEach items="${users}" var="item">
<tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	<td><input type='checkbox' id='ids' name='ids' value="${item.id}"/></td>
	<td>${item.id}</td>
	<td>${item.loginName}</td>
	<td>${item.realName}</td>
	<td>${item.email}</td>
	<c:choose>
		<c:when test="${item.isAdmin==0}">
			<td align="center">否</td>
		</c:when>
		<c:otherwise>
			<td align="center">是</td>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${item.disabled==0}">
			<td align="center">正常</td>
		</c:when>
		<c:otherwise>
			<td align="center">已禁用</td>
		</c:otherwise>
	</c:choose>
	<td align="center">	
		<a href="<%=basePath%>user/toEdit?userId=${item.id}" class="pn-opt">修改</a> 
		<c:choose>
			<c:when test="${item.disabled==0 }">
				<c:if test="${item.id != userId}">
				| 
					<a href="<%=basePath%>user/delete?userId=${item.id}" class="pn-opt" onclick="return confirm('确定要禁用？');">禁用</a>
				</c:if>
			</c:when>
			<c:otherwise>
			| 
				<a href="<%=basePath%>user/active?userId=${item.id}" class="pn-opt" >激活</a>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</form>
</div>
</body>
</html>