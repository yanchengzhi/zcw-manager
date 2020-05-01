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
    <title>密码重置页面</title>
    <%@ include file="/WEB-INF/commons/common-css.jsp" %>
    <link rel="stylesheet" href="${APP_PATH}/static/css/login.css">
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">众筹网-密码重置</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form class="form-signin" role="form" id="resetForm" action="${APP_PATH}/updatePass" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 重置密码</h2>
        <!-- 这个隐藏的文本框传用户ID -->
        <input type="hidden" name="token" value="${param.token}">
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" name="password" id="pass_input" placeholder="请输入新密码" autofocus>
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback" style="margin-top:15px">
		    <input type="password" class="form-control" id="rePass_input" placeholder="确认新密码" autofocus>
		    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
		    <p class="help-block label label-warning" id="info_mess" style="font-size:16px"></p>
		  </div>
        <a class="btn btn-lg btn-success btn-block" id="resetBtn">重置密码</a>
      </form>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script>
     $('#resetBtn').click(function(){
    	 var pass = $('#pass_input').val();
    	 var rePass = $('#rePass_input').val();
    	 //先校验为空
    	 if(pass==""){
    		 $('#info_mess').text("请设置新密码！");
    		 return;
    	 }
    	 if(rePass==""){
    		 $('#info_mess').text("请确认新密码！");
    		 return;
    	 }
    	 //再校验两次密码输入是否一致
    	 if(pass!=rePass){
    		 $('#info_mess').text("两次密码输入不一致！");
    		 return;
    	 }
    	 $('#info_mess').text("");
    	 //通过后提交表单
    	 $('#resetForm').submit();
     });
    </script>
  </body>
</html>