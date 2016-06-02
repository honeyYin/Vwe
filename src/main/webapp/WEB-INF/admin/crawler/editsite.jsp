<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<%=basePath%>res/common/js/jquery.js"></script>	
<script type="text/javascript">
	callAddAction(){
		var title = $('#title')[0].value;
		var url = $('#url')[0].value;
		if(title ==null || title ==""){
			alert("站点名称不能为空");
		}else if(url == null || url == ""){
			alert("站点链接不能为空");
		}else{
			$("#newsForm")[0].submit();
		}
	}
</script>
</head>
<body>
    <div class="box-positon">
         <h1>您所在的位置: 站点管理 - 修改</h1>
        <form class="ropt" action="<%=basePath%>crawler/getSitesList" method="get">
            <input type="submit" value="返回列表"  class="return-button"/>
        </form>
        <div class="clear"></div>
    </div>

    <div class="body-box">
        <form method="post" id="newsForm"  name="newsForm" action="<%=basePath%>crawler/add" >
        	<input type ="hidden" id="siteId" name="siteId" value="${siteId }"/>
             <table width="100%" class="pn-ftable" cellpadding="1" cellspacing="1" border="0">
                
               	<tr>
                    <td width="20%" class="pn-flabel pn-flabel-h">
                        <span class="pn-frequired">*</span>站点名称:
                    </td>
                    <td colspan="1" width="40%" class="pn-fcontent">
                        <input type="text" maxlength="100" id="title" name="title" value="${title}" class="required" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>站点链接:</td>
                    <td colspan="3" width="90%" class="pn-fcontent">
                        <input type="text" maxlength="100" id="url" name="url" value="${url}" class="required" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" class="pn-fbutton">
                        <input type="hidden" name="parentId" value="0"/>
                        <input type="submit" value="提交" class="submit" onclick="javascript:callAddAction()"/> &nbsp;
                        <input type="reset" value="重置" class="reset" />
                    </td>
                </tr>
             </table>
    </form>
</div>

</body>
</html>