<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
int i = 1;

int [] j = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

int [] k = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link href="<%=basePath%>res/common/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>res/common/css/theme.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>res/common/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/AjaxUpload.js"></script>
<!-- 添加页面元素 -->
<script type="text/javascript" src="<%=basePath%>res/common/js/updateElement.js"></script>
<script type="text/javascript" src="<%=basePath%>res/common/js/paperCommon.js"></script>
<!-- 富文本编辑器 -->
<link rel="stylesheet" href="<%=basePath%>res/thirdparty/kindeditor/themes/default/default.css"/>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath%>res/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script>
//判断标题是否为空，不为空则提交表单
<%-- KindEditor.ready(function(K) {

	var editor1 = K.create('#content', {

	cssPath : '<%=basePath%>res/thirdparty/kindeditor/plugins/code/prettify.css',

	uploadJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/upload_json.jsp',

	fileManagerJson : '<%=basePath%>res/thirdparty/kindeditor/jsp/file_manager_json.jsp',

	allowFileManager : true
	})

}); --%>
function callupdateNewsAction(){
	var paperId = $('#paperId')[0].value;
	var title = $('#title')[0].value;
	var titleImg = $('#titleImg')[0].value;
	var pregStage = $('#pregStage')[0].value;
	var recPregWeeks = $('#recPregWeeks')[0].value;
	
	var r,re;
    re = /\d*/i; //\d表示数字,*表示匹配多个数字
    r = recPregWeeks.match(re);
    
	if(title==null || title==""){
		alert("标题不能为空");
	}else if(titleImg == null || titleImg == ""){
		alert("标题图片不能为空");
	}else if(pregStage == 2 && (r != recPregWeeks || recPregWeeks < 0 || recPregWeeks > 40)){
		alert("孕周填写不合法");
	}else{
		$("#newsForm")[0].action="<%=basePath%>paper/edit";
		$("#newsForm")[0].submit();
	}
} 
function showrecPreg(){
	var pregStage = document.getElementById("pregStage").value;
	if(pregStage == 2){
		document.getElementById("div_recPreg").style.display="";
	}else{
		document.getElementById("div_recPreg").style.display="none";
	}
      
}
</script>

</head>

<body>
<div class="box-positon">
	<div class="rpos">当前位置: 文章管理  - 修改</div>
	<form class="ropt" action="<%=basePath%>paper/list" method="get">
		<input type="hidden" id="queryTitle"  name="queryTitle" value="${queryTitle}" />
		<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>
		<input type="hidden" id="channelId"  name="channelId" value="${channelId}"/>
		<input class="return-button" type="submit" value="返回列表" />
	</form>
	<div class="clear"></div>
</div>

<div >

<form id="newsForm"  name="newsForm" method="post" action="javascript:callupdateNewsAction()">
<input type="hidden" id="paperId"  name="paperId" value="${paper.id}"/>
<input type="hidden" id="pageNo"  name="pageNo" value="${pageNo}"/>

<!-- 文章正文区域start -->
<div id="div-content">

<table width="100%"  cellpadding="2" cellspacing="1" border="0">
<tr id="tr-channel">
<td width="10%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>栏目:</td>
<td colspan="3" width="90%" class="pn-fcontent" >
<div style="float:left">
<div>
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
<div id="channelsContainer"></div>
</div>
<div class="clear"></div>
</td>
</tr>

<tr id="tr-title">
<td width="10%" class="pn-flabel pn-flabel-h">
	<input id="paperId" name="paperId" type="hidden" value="${paper.id}"/>
	<span class="pn-frequired">*</span>标题:</td>
<td colspan="3" width="90%" class="pn-fcontent">
<input id="title"  type="text" class="required" name="title"  value = "${paper.title}" size="70" maxlength="150"/>
</td>
</tr>

<tr id="tr-description">
<td width="10%" class="pn-flabel pn-flabel-h">摘要:</td>
<td colspan="3" width="90%" class="pn-fcontent" >
<textarea id ="description"  cols="70" rows="3" maxlength="255"  name="description" >${paper.description}</textarea>
</td>
</tr>

<tr id="tr-author">
<td width="10%" class="pn-flabel pn-flabel-h">作者:</td><td colspan="1" width="40%" class="pn-fcontent">
<input id ="author" type="text" maxlength="100" name="author" value = "${paper.author}"/>
</td>
</tr>

<tr id="tr-isTop">
<td width="10%" class="pn-flabel pn-flabel-h">固顶级别:</td><td colspan="1" width="40%" class="pn-fcontent">
<c:choose>
	<c:when test="${paper.isTop==0}">
		<select  id="isTop" name="isTop">
			<option id="isTop0" value="0" selected>不固顶</option>
			<option id="isTop1" value="1">固顶</option>
		</select> 
	</c:when>
	<c:otherwise>
		<select  id="isTop" name="isTop">
			<option id="isTop0" value="0">不固顶</option>
			<option id="isTop1" value="1" selected>固顶</option>
		</select>   
	</c:otherwise>
</c:choose>
</td>
</tr>
<tr id="tr-pregWeeks">
<td width="10%" class="pn-flabel pn-flabel-h">推荐孕周:</td>
<td colspan="1" width="10%" class="pn-fcontent">
<select  id="pregStage" name="pregStage" onclick="showrecPreg()">
<c:choose>
	<c:when test="${paper.pregStageCode==1}">
			<option value="0" >全孕周</option>
			<option value="1" selected>40周以上</option>
			<option value="2" >0-40周</option>
	</c:when>
	<c:when test="${paper.pregStageCode==2}">
			<option value="0" >全孕周</option>
			<option value="1" >40周以上</option>
			<option value="2" selected>0-40周</option>
	</c:when>
	<c:otherwise>
			<option value="0" selected>全孕周</option>
			<option value="1" >40周以上</option>
			<option value="2" >0-40周</option> 
	</c:otherwise>
</c:choose>
</select>
<c:choose>
	<c:when test="${paper.pregStageCode==2}">
		<div id = "div_recPreg">
			<input id ="recPregWeeks" type="text" maxlength="20" name="recPregWeeks" value="${paper.recPregWeeks}"/>
			<span class="pn-fhelp">0-40之间的数字</span>
		</div>
	</c:when>
	<c:otherwise>
		<div id = "div_recPreg" style="display:none" >
			<input id ="recPregWeeks" type="text" maxlength="20" name="recPregWeeks" value="${paper.recPregWeeks}"/>
			<span class="pn-fhelp">0-40之间的数字</span>
		</div>
	</c:otherwise>
</c:choose>
</td>
</tr>
<tr id="tr-hospital">
<td width="10%" class="pn-flabel pn-flabel-h">所属医院:</td><td colspan="1" width="40%" class="pn-fcontent">
<input id ="hospital" type="text" maxlength="100" name="hospital" value = "${paper.hospital}"/>
</td>
</tr>
<tr id="tr-titleImg">
 	<td width="10%" class="pn-flabel"><span class='pn-frequired'>*</span>标题图片:</td>
 	<td colspan="1" width="40%" class="pn-fcontent">
       	<input class="button"  type="button" id="btnUploadFile" value="上传图片" onclick="javascript:uploanFile('btnUploadFile','pic','titleImg','picDelet','paper',${paper.id})"/>
        <input type="hidden" id="titleImg" name="titleImg" value="<%=basePath %>${paper.titleImg}"/>
       <span class="pn-fhelp" id="pic">
        <c:choose>
		        	<c:when test = "${paper.titleImg ==null || paper.titleImg== ''}">
		        		无图片<span id = "picDelet" ></span>
		        	</c:when>
		        	<c:otherwise>
		        		<img height ="50" width = "150" src = "<%=basePath%>${paper.titleImg}"/>
		        		<span id = "picDelet"><a href="javascript:deleteImg('paper',${paper.id},'pic','titleImg','picDelet')">删除</a></span>
		        	</c:otherwise>
		</c:choose>
		</span>
 	</td>
</tr>
<!-- 版块区域start -->
<tr>
<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>文章正文:</td>
<td class="pn-fcontent">
	<input type="button" value="添加版块"  onclick="javascript:addNewSection('div-content')" /> &nbsp; 
</td>
</tr>

</table>
<!-- 版块区域 -->
<c:forEach items="${paper.sections}" var="section">

  <div id="div-section<%=i%>">
  	<input type="hidden" id = "sectionId<%=i%>" name = "sectionId<%=i%>" value = "${section.id}" />
  	<table  width="100%"  cellpadding="2" cellspacing="1" border="0">
  	<tr>
	<td width="10%"  class="pn-flabel pn-flabel-h">版块<%=i%>:</td>
	<td colspan="3" width="90%" class="pn-fcontent">
		<input type="button" value="添加小节"  onclick="javascript:addNewPara(<%=i%>,'para')" /> &nbsp; 
		<input type="button" value="添加区域跳转块"  onclick="javascript:addNewOutLink(<%=i%>)" /> &nbsp; 
	</td>
	</tr>
	<tr>
	<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>版块标题:</td>
	<td colspan="3" width="90%" class="pn-fcontent">
	<input id="sectionTitle<%=i%>"  type="text" maxlength="150" name="sectionTitle<%=i%>" class="required" size="70" value="${section.title }"/>
	<input type="button" value="删除"  onclick="javascript:deleteElementAndDB('div-content','div-section<%=i%>','section',${section.id})"/> &nbsp; 
	</td>
	</tr>
  	</table> 
  	<c:forEach items="${section.paras}" var="para">
  	<div id="div-para<%=i%>-<%=j[i]%>">
  	<input type="hidden" id = "paraId<%=i%>-<%=j[i]%>" name = "paraId<%=i%>-<%=j[i]%>" value ="${para.id}" />
  	<table  width="100%"  cellpadding="2" cellspacing="1" border="0">
  		<c:choose>
			<c:when test="${para.title==null || para.title=='' }">
				<tr  style='display:none'>
			</c:when>
			<c:otherwise>
				<tr  >
			</c:otherwise>
		</c:choose>
		<td width="10%"  class="pn-flabel pn-flabel-h">小节:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
			<input type="button" value="添加正文"  onclick="javascript:addNewPara(<%=i%>,'content')" /> &nbsp; 
			<input type="button" value="添加配图"  onclick="javascript:addNewPara(<%=i%>,'image')" /> &nbsp; 
		</td>
		</tr>
		<c:choose>
			<c:when test="${para.title==null || para.title=='' }">
				<tr  style='display:none'>
			</c:when>
			<c:otherwise>
				<tr  >
			</c:otherwise>
		</c:choose>
		<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>小节标题:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
		<input id="paraTitle<%=i%>-<%=j[i]%>"  type="text" maxlength="150" name="paraTitle<%=i%>-<%=j[i]%>" class="required" size="70" value="${para.title }"/>
		<input type="button" value="删除"  onclick="javascript:deleteElementAndDB(<%=i%>,<%=j[i]%>,'para',${para.id})"/> &nbsp; 
		</td>
		</tr>
		<c:choose>
			<c:when test="${para.content==null || para.content=='' }">
				<tr  style='display:none'>
			</c:when>
			<c:otherwise>
				<tr  >
			</c:otherwise>
		</c:choose>
		<td width="10%" class="pn-flabel pn-flabel-h">小节正文:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
			<textarea  id="paraContent<%=i%>-<%=j[i]%>" name="paraContent<%=i%>-<%=j[i]%>" cols="70" rows="10">${para.content}</textarea>
		</td>
		</tr>
		<c:choose>
			<c:when test="${para.imgUrl==null || para.imgUrl=='' }">
				<tr  style='display:none'>
			</c:when>
			<c:otherwise>
				<tr  >
			</c:otherwise>
		</c:choose>
		
		 <td width="10%" class="pn-flabel">小节配图:</td>
		 <td colspan="1" width="40%" class="pn-fcontent">
		        <input class="button"  type="button" id="paraBtnUploadFile<%=i%>-<%=j[i]%>" value="上传图片" onclick="javascript:uploanFile('paraBtnUploadFile<%=i%>-<%=j[i]%>','paraPic<%=i%>-<%=j[i]%>','paraTitleImg<%=i%>-<%=j[i]%>','paraPicDele<%=i%>-<%=j[i]%>','para',${para.id })"/>
		        <input type="hidden" id="paraTitleImg<%=i%>-<%=j[i]%>" name="paraTitleImg<%=i%>-<%=j[i]%>" value="<%=basePath %>${para.imgUrl }"/>
		        <c:choose>
		        	<c:when test = "${para.imgUrl == null || para.imgUrl == ''}">
		        		<span class="pn-fhelp" id="paraPic<%=i%>-<%=j[i]%>">无图片</span><span id = "paraPicDele<%=i%>-<%=j[i]%>"></span>
		        	</c:when>
		        	<c:otherwise>
		        		<span class="pn-fhelp" id="paraPic<%=i%>-<%=j[i]%>"><img height ="50" width = "150" src = "<%=basePath%>${para.imgUrl}"/></span>
		        		<span id = "paraPicDele<%=i%>-<%=j[i]%>"><a href="javascript:deleteImg('para',${para.id},'paraPic<%=i%>-<%=j[i]%>','paraTitleImg<%=i%>-<%=j[i]%>','paraPicDele<%=i%>-<%=j[i]%>')">删除</a></span>
		        	</c:otherwise>
		        </c:choose>
		        
		 </td>
		</tr>
  	</table>
  	</div>
  	
<% j[i] = j[i]+1 ;%> 
  	</c:forEach>
  	<c:forEach items="${section.outLinks}" var="outLink">
  	<div id="div-outLink<%=i%>-<%=k[i]%>">
  	<input type="hidden" id = "outLinkId<%=i%>-<%=k[i]%>" name = "outLinkId<%=i%>-<%=k[i]%>" value = "${outLink.id}" />
  	<table  width="100%"  cellpadding="2" cellspacing="1" border="0">
  		<tr>
		<td width="10%"  class="pn-flabel pn-flabel-h">跳转区域<%=i%>-<%=k[i]%>:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
		</td>
		</tr>
		<tr >
		<td width="10%"  class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>标题:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
		<input id="outTitle<%=i%>-<%=k[i]%>"  type="text" maxlength="150" name="outTitle<%=i%>-<%=k[i]%>" class="required" size="70" value="${outLink.title }" />
		<input type="button" value="删除" onclick="javascript:deleteElementAndDB(<%=i%>,<%=k[i]%>,'outLink',${outLink.id})"/> &nbsp; 
		</td>
		</tr>
		<tr>
		<td width="10%" class="pn-flabel pn-flabel-h">副标题:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
			<input id="outSecTitle<%=i%>-<%=k[i]%>"  type="text" maxlength="150" name="outSecTitle<%=i%>-<%=k[i]%>" class="required" size="70" value="${outLink.secTitle }" />
		</td>
		</tr>
		<tr>
		<td width="10%" class="pn-flabel pn-flabel-h">金额:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
			<input id="outPrize<%=i%>-<%=k[i]%>"  type="text" maxlength="50" name="outPrize<%=i%>-<%=k[i]%>" class="required" size="20" value="${outLink.prize }" />
		</td>
		</tr>
		<tr >
		<td width="10%" class="pn-flabel pn-flabel-h">链接:</td>
		<td colspan="3" width="90%" class="pn-fcontent">
			<input id="outUrl<%=i%>-<%=k[i]%>"  type="text" maxlength="150" name="outUrl<%=i%>-<%=k[i]%>" class="required" size="70" value="${outLink.outerUrl }" />
		</td>
		</tr>
  	</table>
  	</div>
<% k[i] = k[i]+1; %>   
  	</c:forEach>   
  </div> 
 <% i = i+1; %>         	   		
</c:forEach>
</div>
<!-- 文章正文区域end -->

<table width="100%"  cellpadding="2" cellspacing="1" border="0">
<tr>
<td colspan="4" class="pn-fbutton">
	<input type="submit" value="提交"  class="submit" /> &nbsp; 
	<input type="reset" value="重置" class="reset"/>
</td>
</tr>
</form>
</div>
<script type="text/javascript">
function uploanFile(buttonId,picId,titleImgId,spanId,type,deleId){
	alert(type);
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
			//var	json = eval( msg );
			
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

var sectionId = <%=i%>;
var arrPara =new Array(<%=j.length%>);
var arrOutLink = new Array(<%=k.length%>);
<%
	for(int t = 0;t<j.length;t++){
%>		
arrPara[<%=t%>] = <%=j[t]%>;
arrOutLink[<%=t%>] = <%=k[t]%>;
<%
	}
%>
</script>
</body>
</html>