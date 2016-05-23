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
	<script type="text/javascript" src="res/common/js/AjaxUpload.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/common/js/jquery.js"></script>	
</head>
<body>
    <div class="box-positon">
    	 <h1>您所在的位置: 栏目管理 - 修改</h1>
        <form class="ropt" action="<%=basePath%>channel/index" method="get">
        <!-- 返回 父类的列表 -->
            <input type="submit" value="返回列表"  class="return-button"/>
        </form>
        <div class="clear"></div>
    </div>

    <div class="body-box">
        <form method="post" action="javascript:callAddNewsAction()" id="addChannelF">
             <table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
                <tr>
                    <td width="10%" class="pn-flabel pn-flabel-h">
                        <span class="pn-frequired">*</span>栏目名称:
                    </td>
                    <td colspan="1" width="40%" class="pn-fcontent">
                        <input type="text" maxlength="100" id="name" name="name" value="${channel.name }" class="required" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <td width="10%" class="pn-flabel pn-flabel-h">栏目描述:</td>
                    <td colspan="3" width="90%" class="pn-fcontent">
                        <textarea cols="70" rows="3" name="description" maxlength="255">${channel.description }</textarea>
                    </td>
                </tr>
                
                <tr>
                    <td width="20%" class="pn-flabel pn-flabel-h">栏目图片:</td>
                    <td colspan="1" width="90%" class="pn-fcontent">
                        <c:choose>
                        	<c:when test="${channel.titleImg =='no'}">
                        	       <input class="button" type="button" id="btnUploadFile" value="上传图片" />
			                        <input type="hidden" id="titleImg" name="titleImg" value="no"/>
			                        <span class="pn-fhelp" id="pic">无图片</span>
                        	</c:when>
                        	<c:otherwise>
                         	       <input class="button" type="button" id="btnUploadFile" value="修改图片" />
			                        <input type="hidden" id="titleImg" name="titleImg" value="${channel.titleImg }"/>
			                        <span class="pn-fhelp" id="pic"><img height ='50' width = '150' src = "<%=basePath%>${channel.titleImg }"/></span>                       	
                        	</c:otherwise>
                        </c:choose>
                    </td>
                </tr> 
                                
                <tr>
                    <td width="10%" class="pn-flabel pn-flabel-h">栏目每页记录数:</td>
                    <td colspan="3" width="90%" class="pn-fcontent">
                    	<input type="text" name="pageSize" value="${channel.pageSize }" style="width:40px" class="digits" maxlength="5"/>
                    </td>
                </tr>
 
                <tr>
                    <td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>排列顺序:</td>
                    <td colspan="1" width="40%" class="pn-fcontent">
                        <input type="text" maxlength="255" id="priority" name="priority" value="${channel.priority}" class="reuqired digits" size="10" maxlength="255"/>
                    </td>
                </tr>

                <tr>
                    <td width="10%" class="pn-flabel pn-flabel-h">
                        <span class="pn-frequired">*</span>显示:
                    </td>
                    <td colspan="1" width="40%" class="pn-fcontent">
                    	<c:choose>
		                	<c:when test="${channel.isDeploy == 0 }">
                        		<label><input type="radio" value="1" checked="checked" name="isDeploy"/>是</label>
                        		<label><input type="radio" value="0" name="isDeploy"/>否</label>&nbsp;		                	
		                	</c:when>
		                	<c:otherwise>
		                        <label><input type="radio" value="1"  name="isDeploy"/>是</label>
                       		 	<label><input type="radio" value="0" checked="checked" name="isDeploy"/>否</label>&nbsp; 	
		                	</c:otherwise>
		                </c:choose>
                    </td>
                </tr>
                 <tr>
                    <td colspan="4" class="pn-fbutton">
                    	 <input type="hidden" name="channelId" value="${channel.id }"/>
                        <input type="submit" value="提交" class="submit" class="submit"/> &nbsp;
                    </td>
                </tr>
             </table>
    </form>
</div>
<script type="text/javascript">
	function cutpres(msg)
	{
		var start = msg.indexOf("{");
		return msg.substr(start).replace("</pre>", "");
	}
	var button = document.getElementById("btnUploadFile"); 
	var ajaxUploadImage = new AjaxUpload(button,{
	
		action: '<%=basePath%>upload',
		autoSubmit: true, //交由确定按钮提交
		name: 'filedata',   
		
		onChange:function(file,ext){//当选择文件后执行的方法,ext存在文件后续,可以在这里判断文件格式
			hasAddSpecialImage=true;
		},
			        
		onSubmit : function(file, ext){
		    if (!(ext && /^(jpg|JPG|png|PNG|gif|GIF)$/.test(ext))) {  
		        alert("您上传的图片格式不对，仅能上传jpg、JPG、png、PNG、gif、GIF的图片，请重新选择！");  
		        return false;  
		    }  
		
			 this.disable();
		},
		
		onComplete: function(file, response){ //上传完毕后的操作	
			var start = response.indexOf("{");
			var end = response.indexOf("}");
			var	msg = response.substring((start+1), end);
			
			if(msg=="size"){
				alert("文件大于1M");
			}else{
				document.getElementById("pic").innerHTML = "<img height ='50' width = '150' src = '<%=basePath%>" + msg + "'/>";
				document.getElementById("titleImg").value = msg;
			}
		
			this.enable();
	}
	});
	
	function callAddNewsAction(){
		//判断
		var title = $("#name").val();
		var pro = $("#priority").val();
		//action="modifyChannel.do"
		if(title==null || title==""){
			alert("栏目名不能为空");
		}else if(pro==null || pro==""){
			alert("排列顺序不能为空");
		}
		else{
			$("#addChannelF")[0].action="<%=basePath%>channel/edit";
			$("#addChannelF")[0].submit();
		};
	}
</script>
</body>
</html>