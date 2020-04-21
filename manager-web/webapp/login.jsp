<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <title>登录界面</title>
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

      <form class="form-signin" role="form" id="loginForm" action="${APP_PATH}/permission/user/login" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="loginacct" id="login_input" value="${errorUser.loginacct}" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
			<span style="color:red;font-size:15px">${msg}</span>
			<!-- 取出一次后将Sesion中的该对象移除掉 -->
			<c:remove var="msg"/>
			<c:remove var="errorUser"/>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" name="userpswd" id="userpswd_input" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
			    <option value="manager">管理</option>
                <option value="member">会员</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="${APP_PATH}/register.jsp">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script>
    function dologin() {
        var type = $(":selected").val();
        if ( type == "manager" ) {//管理员登录
            //表单提交
            $('#loginForm').submit();
        
        } else {//跳转到默认门户网站
            alert("此功能尚未开通！")
        }
        return;
    }
    </script>
  </body>
</html>