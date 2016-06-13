<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>一键抓取管理</title>
    
    <link href="<%=basePath%>res/thirdparty/treeview/css/jquery.tree.css" rel="stylesheet" type="text/css" />
   
    <script src="<%=basePath%>res/thirdparty/treeview/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>res/thirdparty/treeview/js/jquery.tree.js" type="text/javascript" ></script>
    
    <script type="text/javascript">
        function toCrawler(){
        	window.parent.frames["rightFrame"].location.href = "<%=basePath%>crawler/index";
        }
        function toSites(){
        	window.parent.frames["rightFrame"].location.href = "<%=basePath%>crawler/getSitesList";
        }
    </script>
</head>
<body>
	<div class="left">
	<%@include file="../date.jsp" %>
	<div class="fresh">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
		 	<tr>
	            <td height="35" align="center"><a >一键抓取</a></td>
	      	 </tr>
	          <tr>
	            <td height="35" align="center"><a href="javascript:toCrawler()" >文章抓取</a></td>
	      	 </tr>
	      	 <tr>
	            <td height="35" align="center"><a href="javascript:toSites()" >站点管理</a></td>
	      	 </tr>
	     </table>
	</div>
	     <div id="tree">
	            
	      </div>
	 </div>

</body>
</html>
