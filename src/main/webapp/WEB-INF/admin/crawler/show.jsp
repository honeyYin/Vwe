<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>一键抓取</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>res/common/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=basePath%>res/common/js/AjaxUpload.js"></script>
	<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>res/common/js/jquery-ui.js" language="javascript" type="text/javascript" ></script>

</head>
<script>
	$(function() {
    $( "#dialog" ).dialog({
      autoOpen: false,
      show: {
        effect: "blind",
        duration: 1000
      },
      hide: {
        effect: "explode",
        duration: 1000
      },
      height:240,
      width:512,
      
      modal: true
    });
  });
	function callAddPaper(){
        	var title = $('#title')[0].value;
        	var paperurl = $('#paperurl')[0].value;
    		var otitleImg = $('#otitleImg')[0].value;
    		if(title ==null || title ==""){
    			alert("标题不能为空");
    		}else if(otitleImg == null || otitleImg == ""){
    			alert("标题图片不能为空");
    		}else if(paperurl == null || paperurl ==""){
    			alert("文章链接不能为空");
    		}else{
    			$("#newsForm")[0].action = "<%=basePath%>paper/addCrawlerPaper";
    			$("#newsForm")[0].submit();
              	$( this ).dialog( "close" );
    		}
	}
	function cacleDialog(){
		$("#dialog").dialog( "close" );
	}
	function $id(id){
		return document.getElementById(id);
	}
	function changeUrl(){
		var id = document.getElementById("siteId").value;
		var url = document.getElementById("urlValue"+id).value;
		document.getElementById("url").value = url;
	}
	function toAddPaper(title,url,orderId){
		
		var par_data="type=1&title="+title+"&paperurl="+url;
		$.ajax({ 
			 type: "POST", 
			 url: "<%=basePath%>paper/addCrawlerPaper",  
			 data: par_data, 
			 success: function(message){ 
				//alert(message);
				if("succ"== message){//可用
					$id(orderId).innerHTML = "已入库"
					alert("入库成功，请至[外链文章]栏目中查看");
				}else{
					
				}
		}});
		
		//弹框式添加
		/* $id("title").value=title;
		$id("paperurl").value = url;
		$( "#dialog" ).dialog( "open" );  */
	}
	function uploanFile(buttonId,picId,titleImgId,spanId,type,deleId){
		var button = document.getElementById(buttonId); 
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
					document.getElementById(picId).innerHTML = "<img height ='50' width = '150' src = '<%=basePath%>" + msg + "'/>";
					document.getElementById(titleImgId).value = msg;
					document.getElementById(spanId).innerHTML="<a href=\"javascript:deleteImg('"+type+"','"+deleId+"','"+picId+"','"+titleImgId+"','"+spanId+"')\">删除</a>";
				}
			
				this.enable();
		}
		});	
	}	
</script>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 一键抓取</div>
	<div class="clear"></div>
</div>

<div class="body-box">
	<div style="margin-top:15px;line-height:34px;font-size:12px;" >
		<form name="form1" id ="form1"  action="<%=basePath%>crawler/queryByCondition" method ="get" padding-top:5px;">
		<div >
			&nbsp;目标站点: 
			<select id="siteId" name="siteId" style="height:25px;width:185px" onchange="javascript:changeUrl()">
				<option value="0" selected="selected">--请选择抓取的目标网站--</option>
				<c:forEach items="${sites}" var="v_site">
				<c:choose>
					<c:when test="${v_site.url == url}">
						<option value="${v_site.id }"  selected>${v_site.title }</option>
					</c:when>
					<c:otherwise>
						<option value="${v_site.id }" >${v_site.title }</option>
					</c:otherwise>
				</c:choose>
					
				</c:forEach>
			</select>
			<input type="text" id="url"  name="url" value="${url}" size="75" style="margin-left:22px;height:25px;"/>
		</div>
		<div >
			&nbsp;&nbsp;关键词: <input type="text" id="queryTitle"  name="queryTitle" value="${queryTitle}"  style="height:25px;width:185px"/>
			<input class="query" type="submit" value="搜索" style="margin-left:22px;"/>
		</div>
		<div>
			<c:forEach items="${sites}" var="v_site">
				<input type="hidden" id="urlValue${v_site.id }" name="urlValue${v_site.id }" value="${v_site.url}"/>
			</c:forEach>
		</div>
		</form>
	</div>
    <form id="tableForm" name="listform">
        <table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
            <thead class="pn-lthead">
            	<tr>
	                <th>序号</th>
	                <th>名称</th>
	                <th>链接</th>
	                <th>操作选项</th>
                </tr>
            </thead>
            <tbody  class="pn-ltbody">
           <c:forEach var="item" items="${links}">
	            <tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
	                <td align="center" width="10%">
	                	${item.order }
	                </td>
	                <td width="30%">
	                	<a style="width:200px" onclick="window.open('${item.url }')">${item.title }</a>
	                </td>
	                <td align="left" width="40%">
	                	<a style="width:200px" onclick="window.open('${item.url }')">${item.trimUrl }</a>
	                </td>
	                <td align="center" width="20%" id="a_add${item.order}">		
	                	<a class="pn-opt" href="javascript:toAddPaper('${item.title }','${item.url }','a_add${item.order}')">入库</a>
	                </td>
	            </tr>
            </c:forEach>
           </tbody>
        </table>
    </form>
    <div id="dialog" title="添加文章" style="height:180px">
	<form id="newsForm" name="newsForm" method="post" >
    	<table width="100%"  cellpadding="2" cellspacing="1" border="0">
			<tr id="tr-channel">
			<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>栏目:</td>
			<td colspan="3" width="90%" class="pn-fcontent" >
			<div style="float:left">
			     <select id="channelId"  name="channelId">
			         <c:forEach items="${channels}" var="item">
			              <c:choose>
							   <c:when test="${item.id == channelId}">
							          <option value="${item.id }" selected="selected">${item.name }</option>
							   </c:when>
							   <c:otherwise>
							          <option value="${item.id }" >${item.name }</option>
							   </c:otherwise>
							   </c:choose>
			           </c:forEach>
			      </select>                	       			
			</div>
			<div class="clear"></div>
			</td>
			</tr>
			
			<tr id="tr-title">
			<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>标题:</td>
			<td colspan="3" width="90%" class="pn-fcontent">
			<input id="title"  type="text"  name="title" class="required" size="30" maxlength="16" style="height:25px;"/>
			</td>
			</tr>
			<tr id="tr-type">
			<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>文章类型:</td>
			<td colspan="1" width="40%" class="pn-fcontent">
				<input type="hidden" id="type" name="type" value ="1">
				<span style="height:25px;">外链</span></td>
			</tr>
			<tr id="tr-paperImg" >
				 <td width="10%" class="pn-flabel"><span class='pn-frequired'>*</span>标题图片:</td>
				 <td colspan="1" width="40%" class="pn-fcontent">
				        <input class="button"  type="button" id="obtnUploadFile" value="上传图片" onclick="javascript:uploanFile('obtnUploadFile','opic','otitleImg','opicDelet','paper','')" />
				        <input type="hidden" id="otitleImg" name="otitleImg" value=""/>
				        <span class="pn-fhelp" id="opic">无图片</span><span id = "opicDelet" ></span>
				 </td>
			</tr>
			<tr id="tr-paperurl">
				<td width="10%" class="pn-flabel pn-flabel-h"><span class='pn-frequired'>*</span>链接:</td>
				<td colspan="1" width="40%" class="pn-fcontent">
					<input id ="paperurl" type="text"  name="paperurl" size="30" style="height:25px;"/>
				</td>
			</tr>
		</table>
		<table width="100%"  cellpadding="2" cellspacing="1" border="0">
			<tr>
			<td colspan="4" class="pn-fbutton">
				<input type="submit" value="提交"  class="submit" onclick= "javascript:callAddPaper()"/> &nbsp; 
				<input type="button" value="取消" class="reset" onclick="javascript:cacleDialog()"/>
			</td>
			</tr>
		</table>
	</form>
    </div>
</div>
<script type="text/javascript">
/* 初始化上传控件 */
 uploanFile('obtnUploadFile','opic','otitleImg','opicDelet','paper','');

</script>
</body>
</html>