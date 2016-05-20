<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>栏目管理</title>
    
    <link href="<%=basePath%>res/thirdparty/treeview/css/jquery.tree.css" rel="stylesheet" type="text/css" />
   
    <script src="<%=basePath%>res/thirdparty/treeview/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>res/thirdparty/treeview/js/jquery.tree.js" type="text/javascript" ></script>
    
    <script type="text/javascript">
        function getChannelList(){
        	window.parent.frames["rightFrame"].location.href = "<%=basePath%>channel/index";
        }
    </script>
</head>
<body>
	<div class="left">
	<%@include file="../date.jsp" %>
	<div class="fresh">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="35" align="center"><a href="javascript:getChannelList()" >所有栏目</a></td>
	      	 </tr>
	     </table>
	</div>
	     <div id="tree">
	            
	      </div>
	 </div>

</body>
</html>
