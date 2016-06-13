<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="head.jsp" %>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>jeecms-left</title>

</head>
<body>
  	    <div class="box-positon">
  	    <div class="rpos">当前位置: 后台首页</div>
			<div class="clear"></div>
        </div>
<div class="body-box" >
        <div class="welcom-con">
        <h1>CMS系统说明</h1>
        <h2> 用户：</h2>
        <h3>管理员</h3>  
        	<div style="padding-left:15px;font-size:16px"> 
        		a.创建管理员或普通用户<br/>
	        	b.修改管理员或普通用户基本信息<br/>
				c.禁用或激活普通用户
			</div>
		<h3>普通用户 </h3>
		<div style="padding-left:15px;font-size:16px">修改当前用户信息</div>
	
		<h2> 栏目：</h2>
		<div style="padding-left:15px;font-size:16px">
			a.添加栏目信息包括名称、描述、图片、排列顺序等<br/>
			b.修改栏目信息包括名称、描述、图片、排列顺序等<br/>
			c.删除栏目<br/>
			d.修改栏目排序，修改排列顺序并保存排列顺序，修改操作影响文章页左侧栏目排序
		</div>
		<h2>文章：</h2>
		<div style="padding-left:15px;font-size:16px">
			a.添加文章信息包括栏目、标题、摘要、作者、头图、正文等，其中栏目、标题、标题图片（头图）为必填项<br/>
			b.新添加的文章，可查看、修改、上移或下移、删除、发布、置顶<br/>
			c.文章排序规则，固定（置顶）、发布、未发布，其中未发布文章将不出现app端文章列表<br/>
			d.批量操作，批量删除与发布<br/>
			e.搜搜，可依据标题进行搜索<br/>
			f.文章可依据需要添加多个板块，保存板块信息时需有板块标题<br/>
			g.一个板块可添加多个小节、小节正文、小节图片
		</div>

        </div>
             
  </div>
</body>
</html>