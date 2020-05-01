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
    <title>提示页面</title>
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
      <form class="form-signin" role="form">
        <h2 class="form-signin-heading">${msg}</h2>
      </form>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
  </body>
</html>