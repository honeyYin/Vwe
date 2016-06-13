<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文章管理</title>
    
    <link href="<%=basePath%>res/thirdparty/treeview/css/jquery.tree.css" rel="stylesheet" type="text/css" />
   
    <script src="<%=basePath%>res/thirdparty/treeview/js/jquery.js" type="text/javascript"></script>
    <!--
    <script src="js/common.js" type="text/javascript" ></script>
    -->
	<script src="<%=basePath%>res/thirdparty/treeview/js/jquery.tree.js" type="text/javascript" ></script>
    <!--
    <script src="js/tree1.js" type="text/javascript" ></script>
    -->
    <%--<script src="<%=basePath%>thirdparty/treeview/js/tree2.js" type="text/javascript" ></script>--%>
    
    <script type="text/javascript">
    function getChannelList(channelId){
    		window.parent.frames["rightFrame"].location.href = "<%=basePath%>paper/list?pageNo=0&channelId="+channelId;
    }
    function queryDraft(){
		window.parent.frames["rightFrame"].location.href = "<%=basePath%>paper/queryByCondition?pageNo=0&isDraft=1";
	}
    </script>
</head>
<body>
	<div class="left">
	<%@include file="../date.jsp" %>
	<div class="fresh">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
		 	<tr>
	            <td height="35" align="center"><a href="javascript:getChannelList(0)" >所有文章</a></td>
	      	 </tr>
	      	 <c:forEach items="${channels}" var="item"> 
	      	 	<tr>
	            	<td height="35" align="center"><a href="javascript:getChannelList(${item.id})" >${item.name}</a></td>
	            </tr>
	      	 </c:forEach>
	      	 <tr>
	            	<td height="35" align="center"><a href="javascript:queryDraft()" >草稿</a></td>
	         </tr>
	     </table>
	</div>
	     <div id="tree">
	      </div>
	 </div>

</body>
</html>
