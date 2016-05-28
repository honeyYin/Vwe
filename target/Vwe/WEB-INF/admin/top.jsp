<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="head.jsp" %>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>top</title>

<style type="text/css">        
*{margin:0;padding:0}
a:focus {outline:none;}
html{height:100%;overflow:hidden;}
body{height:100%;}
#top{ background-color:#1d63c6; height:69px; width:100%;}
.logo{width:215px; height:69px;}
.topbg{background:url(<%=basePath%>res/tycms/img/admin/top-tbg.png) no-repeat; height:38px;}
.login-welcome{padding-left:20px; color:#fff; font-size:12px;background:url(<%=basePath%>res/tycms/img/admin/topbg.gif) no-repeat;}
.login-welcome a:link,.login-welcome a:visited{color:#fff; text-decoration:none;}

#welcome {color: #FFFFFF;padding: 0 30px 0 5px;}
#logout {color: #FFFFFF; padding-left: 5px;}

.nav{height:31px; overflow:hidden;}
.nav-menu{background:url(<%=basePath%>res/tycms/img/admin/bg.png) repeat-x; height:31px; list-style:none; padding-left:20px; font-size:14px;}
.nav .current {background: url(<%=basePath%>res/tycms/img/admin/navcurrbg.gif) no-repeat 0px 2px; color:#fff; width:72px; text-align:center;} 
.nav .current a{color:#fff;}
.nav-menu li {height:31px;text-align:center; line-height:31px; float:left; }
.nav-menu li a{color:#2b2b2b; font-weight:bold;}
.nav-menu li.sep{background: url(<%=basePath%>res/tycms/img/admin/step.png) no-repeat; width:2px; height:31px; margin:0px 5px;}
.nav .normal{width:72px; text-align:center;}
.top-bottom{width:100%; background: url(<%=basePath%>res/tycms/img/admin/bg.png) repeat-x 0px -34px; height:3px;}
.undis{display:none;}
.dis{display:block;}
</style>

<script type="text/javascript">
function g(o){
	return document.getElementById(o);
}
$(function(){

	$('.nav-menu li a').click(function(){
	        $('.nav-menu li').removeClass('current');
	        $('.nav-menu li').addClass('normal');
	        $(this).parent().addClass('current');

	   })

	})
</script>
<script> 
	
</script> 
</head>

<body>
<div id="top">
     <div class="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="215"><div class="logo"><img src="<%=basePath%>res/tycms/img/admin/logo1.png" width="215" height="69" /></div></td>
            <td valign="top">
                <div class="topbg">
                     <div class="login-welcome">
                             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="420" height="38">
                                    <img src="<%=basePath%>res/tycms/img/admin/welconlogin-icon.png"/><span id="welcome">欢迎您登录：${sessionScope.user.realName }</span>
                                    <img src="<%=basePath%>res/tycms/img/admin/loginout-icon.png"/><a href="<%=basePath%>/logout" target="_top" id="logout" onclick="return confirm('确定退出登录?');">退出</a>　　
                                    </td>
                                  </tr>
                                </table>
                       </div>  
                     <div class="nav">
                     	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td style="background-image:url('<%=basePath%>res/tycms/img/admin/nav-left.png')" width="14" height="31"></td>
                                <td>
                                	<ul class="nav-menu">
                                    	<li class="current" id="tb_11"><a href="<%=basePath%>admin/main" target="mainFrame">首页</a></li>
										<li class="sep"></li><li class="normal" id="tb_12" ><a href="<%=basePath%>channel/main" target="mainFrame" >栏目</a></li>
										<li class="sep"></li><li class="normal" id="tb_13"><a href="<%=basePath%>paper/main" target="mainFrame" >文章</a></li>
										<li class="sep"></li><li class="normal" id="tb_19"><a href="<%=basePath%>user/main" target="mainFrame" >用户</a></li>
									</ul>
                                </td>
                              </tr>
                            </table>
                     </div>  
                </div>
          </tr>
        </table>
     </div>
</div>
<div class="top-bottom"></div>
</body>
</html>