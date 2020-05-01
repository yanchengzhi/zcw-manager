<%@page import="com.ycz.zcw.manager.pojo.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.ycz.zcw.manager.pojo.User"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <% 
        //获取所有Cookie
        Cookie[] cookies = request.getCookies();
        //判断Cookie是否为空
        if(cookies!=null && cookies.length>0){
            //增强for循环在数组为null时会抛异常，所以先判断非空
            for(Cookie c:cookies){
                if(c.getName().equals("autoLogin")){
                    //先去缓存库中查（Application域模拟缓存库）
                    User user = (User)application.getAttribute(c.getValue());
                    if(user!=null){//判断域中是否有
                        //查到后放到session域中
                        session.setAttribute(Constants.LOGIN_USER, user);
                        //然后直接跳转到主页面
                        response.sendRedirect(request.getContextPath()+"/main.html");
                    }
                }
            }
        }
    %>
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
            <input type="checkbox" name="rememer" value="1"> 记住我
          </label>
          <label style="float:right;">
          <a href="${APP_PATH}/register.jsp">我要注册</a>
          </label><br>
          <label style="margin-top:7px">
          <!-- 这里用伪静态实现跳转 -->
          <a href="${APP_PATH}/toforgetpwd.html">忘记密码</a>
          </label>
          <br>
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