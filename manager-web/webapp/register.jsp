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
    <title>注册页面</title>
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
      <form class="form-signin" id="regForm" role="form" action="${APP_PATH}/permission/user/regist" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="loginacct" id="loginacct_input" value="${user.loginacct}" placeholder="请输入登录账号">
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
			<!-- 用来显示错误信息 -->
			<span class="error-info" style="color:red;font-size:15px"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" name="userpswd" id="userpswd_input" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			<span class="error-info" style="color:red;font-size:15px"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="email" id="email_input" value="${user.email}" placeholder="请输入邮箱地址" style="margin-top:10px;">
			<span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
			<span class="error-info" style="color:red;font-size:15px"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
			    <!-- 无需提交，可省略name和value值 -->
                <option>会员</option>
                <option>管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="${APP_PATH}/login.jsp">我有账号</a>
          </label>
        </div>
        <a id="submitBtn" class="btn btn-lg btn-success btn-block" > 注册</a>
      </form>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
        <!-- 引入校验插件 -->
    <script src="${APP_PATH}/static/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
    <script type="text/javascript">
       //给校验器设置策略
       $.validator.setDefaults({
    	   showErrors:function(map,list){
    	       //先清除所有错误信息
    	       $('.error-info').empty();
    	       //清空input文本框的样式
    	       $('.form-group').removeClass('has-success has-error has-warning');
    		   $.each(list,function(){
    			   //element为当前错误
    			   //message为错误信息
    			   //method为校验的规则
    			   $(this.element).nextAll(".error-info").text(this.message);//提示错误信息
    			   //改变input文本框的样式
    			   $(this.element).parent('div.form-group').addClass('has-error');
    		   });
    	   }
       });
       $('#submitBtn').click(function(){
    	   //获取下拉框选中的值
    	   var loginType = $('select.form-control').val();
    	   //判断是管理还是会员
    	   if(loginType=="管理"){
        	   //点击注册后提交表单
        	   $('#regForm').submit();
    	   }else{
    		   alert("此功能尚未开通！")
    	   }
    	   return;
       });
       //添加校验规则
       $('#regForm').validate({
    	   onfocusin: function(element) { $(element).valid(); },
    	   rules: {
    		   //规定登录名校验
    		   loginacct:{
    			   required: true,
    			   minlength:5
    		   },
    		   userpswd:{
    			   required: true,
    			   minlength:5
    		   },
    		   email:{
    			  required:true,
    			  email:true
    		   }
    	   },
    	   messages: {
    		   //规定登录名校验
    		   loginacct:{
    			   required: "登录账号不能为空！",
    			   minlength:"账号不少于5位字符！"
    		   },
    		   userpswd:{
    			   required: "登录密码不能为空！",
    			   minlength:"密码不能少于5个字符！"
    		   },
    		   email:{
    			  required:"用户邮箱不能为空！",
    			  email:"请输入正确的邮箱格式！"
    		   }
    	   },
       });
	   //alert("进行校验！");
    </script>
  </body>
</html>