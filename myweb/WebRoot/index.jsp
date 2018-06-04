<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<title>登录界面</title>
<style type="text/css">
body{background-repeat:repeat-x;}
#menu{width:100%; height:50px;position:relative; background-color:#333;}
#center{width:80%; height:500px; position:relative; margin-top:50px; margin-left:10%; background-color:#333;}
#center #left{background-color:#FFF;width:75%; height:500px;}
#center #right{background-color:#333;width:25%; height:500px; margin-left:75%; margin-top:-500px;}
.aa{width:200px; height:25px;}
.bb{width:100px; height:33px; color:#fff; background-color:#333; border:none; font-size:18px;}
a{ color:#fff; text-decoration:none;}
a:hover{ color:#900; text-decoration:underline;}
a:active{ color:#900;}
.cc{width:100px; height: 40px; background-color: #333; color: #FFF; font-size: 20px; border: none; font-family: '楷体'; margin-left: -10px;}
</style>
  </head>
  <script src="jquery-3.3.1.min.js"></script>
  <script type="text/javascript">
  $(function(){
  	$("#getcode").click(function(){
            var name = $("#name").val();
            if (name.length == 0) {
            	$("#name").tips({msg: "请输入用户名。"});
            	return;
            }
            $.ajax({
                cache:true,
                type:"POST",
                url:"/myweb/TestServlet",
                data:{"name": name},
                dataType: "json",
                async:false,
                error:function(request){
                    return;
                },
                success:function(data){
                    console.log("success");
                }
            });
         });
  })
       
    </script>
<body style="background:url(images/image12.png)">
<div id="menu">
<table width="100%" height="50px">
	<tr>
    <td width="50px"></td>
    	<td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">木器</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">瓷器</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">青铜</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">玉器</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">漆器</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">古画</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">古书</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">古饰</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">钟表</a></b></td>
        <td width="50px" style="font-family:楷体; font-size:20px; color:#FFF"><b><a href="#" style="text-decoration:none">织绣</a></b></td>
        <td width="200px">
        	<form><input type="text" class="aa"/><button type="submit" class="bb">search</button></form>
        </td>
    </tr>
</table>
</div>
<div id="center">
<div id="left">
 <form  method="post" action="<%=request.getContextPath() %>/TestServlet">
 <table style="background-color:#fff;height: 100%;width:100%; padding-left: 50px">
    <tr>
        <td width="50px" style="color:black; font-size:32px; font-family:楷体;"><center><b>请登录</b></center></td>
    </tr>
    <tr>
        <td width="50px" style="color:black;font-size:18px; font-family:楷体;"><center>请输入用户名: <input type="text" id="name"/></center></td>
    </tr>
   
    <tr>
        <td width="50px" style="color:black; font-size:18px; font-family:楷体;"><center>请输入密码：<input type="password"  id="password"/></center></td>
    </tr>
    <tr>
        <td  style="color:black; font-size:18px; font-family:楷体;"><center>请输入验证码：<input type="text" id="dongtaipassword"/></center></td>
    </tr>
    <tr>
    <td><center><a href="Untitled-4.html"> <button type="submit" class="bb" value="登录" onclick="alert('登陆成功')"><b>登录</b></button></a></center></td>
    </tr>

</table>
</form>
</div>
<div id="right" style="background:url(images/image12.png);background-size: cover;">
<form id="loginForm" style="padding-left:56px; padding-top:420px">
<input type="button" value="获取验证码" id="getcode" class="bb" style="width: 150px; color:black; background-color:white"/>

</form>
</div>
</div>
</body>
</html>
