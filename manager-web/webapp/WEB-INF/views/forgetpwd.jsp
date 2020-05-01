<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <title>忘记密码--找回</title>
    <%@ include file="/WEB-INF/commons/common-css.jsp" %>
    <link rel="stylesheet" href="${APP_PATH}/static/css/login.css">
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">众筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>
    <div class="container">
      <form class="form-signin" role="form" id="sendForm" action="${APP_PATH}/sendEmail" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 填写邮箱</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="email" id="email" placeholder="请输入注册邮箱" style="margin-top:10px;">
			<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			<p class="help-block label label-warning" id="info_email"></p>
		  </div>
        <a class="btn btn-lg btn-success btn-block" onclick="doSend()"> 确定</a>
      </form>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script>
    //邮箱的校验格式
	var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    //显示提示信息
    $('#email').blur(function(){
    	$('#info_email').text("");
    	var email = $('#email').val();
        if(email==""){
        	$('#info_email').text("邮箱不能为空！");
        	return;
        }
        if(!regEmail.test(email)){
        	$('#info_email').text("邮箱格式不正确！");
        	return;
        }
    });
    
    function doSend() {
    	//先进行邮箱的合法性校验
    	var email = $('#email').val();
        if(email==""){
        	return;
        }
        if(!regEmail.test(email)){
        	return;
        }
        $('#info_email').text("");
       $('#sendForm').submit();
    }
    </script>
  </body>
</html>