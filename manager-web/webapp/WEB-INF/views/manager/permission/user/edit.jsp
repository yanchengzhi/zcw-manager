<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>用户编辑</title>
    <%@ include file="/WEB-INF/commons/common-css.jsp" %>
	<link rel="stylesheet" href="${APP_PATH}/static/css/main.css">
	<link rel="stylesheet" href="${APP_PATH}/static/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>
  <%pageContext.setAttribute("header_info", "用户维护"); %>
  <body>
  <%@ include file="/WEB-INF/commons/common-header.jsp" %>
    <div class="container-fluid">
      <div class="row">
      <%@ include file="/WEB-INF/commons/common-leftbar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">修改</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">登陆账号</label>
					<input type="text" class="form-control" name="loginacct" id="loginacct_input" value="${user.loginacct}">
				    <p class="help-block label label-warning" id="info_loginacct"></p>
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">用户名称</label>
					<input type="text" class="form-control" name="username" id="username_input" value="${user.username}">
				    <p class="help-block label label-warning" id="info_username"></p>
				  </div>
				  <div class="form-group">
					<label for="exampleInputEmail1">邮箱地址</label>
					<input type="email" class="form-control" name="email" id="email_input" value="${user.email}">
					<p class="help-block label label-warning" id="info_email"></p>
				  </div>
				  <button type="button" class="btn btn-success" id="edit_btn"><i class="glyphicon glyphicon-edit"></i> 修改</button>
				  <button type="button" class="btn btn-danger" id="reset_btn"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
            //格式校验
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            $('#loginacct_input').blur(function(){
                var loginacct = $('#loginacct_input').val();
            	if(loginacct==""){
            		$('#info_loginacct').text("登录账号不能为空！");
            		//文本框添加样式
            		$('#loginacct_input').parent().addClass('has-error');
            		return;
            	}
            	if(loginacct.length<5){
            		$('#info_loginacct').text("账号字符长度不能少于5位！");
            		$('#loginacct_input').parent().addClass('has-error');
            		return;
            	}
            	$('#info_loginacct').text("");
            	$('#loginacct_input').parent().removeClass('has-error').addClass('has-success');
            });
            $('#username_input').blur(function(){
                var username = $('#username_input').val();
            	if(username==""){
            		$('#info_username').text("用户名不能为空！");
            		$('#username_input').parent().addClass('has-error');
            		return;
            	}
            	if(username.length<2){
            		$('#info_username').text("用户名长度不能少于2位！");
            		$('#username_input').parent().addClass('has-error');
            		return;
            	}
            	$('#info_username').text("");
            	$('#username_input').parent().removeClass('has-error').addClass('has-success');
            });
            $('#email_input').blur(function(){
            	var email = $('#email_input').val();
            	if(email==""){
            		$('#info_email').text("用户邮箱不能为空！");
            		$('#email_input').parent().addClass('has-error');
            		return;
            	}
            	//邮箱格式校验
            	if(!regEmail.test(email)){//邮箱不匹配时
            		$('#email_input').parent().addClass('has-error');
            		$('#info_email').text("邮箱格式不正确！");
            	}else{
            		$('#email_input').parent().removeClass('has-error').addClass('has-success');
            		$('#info_email').text("");
            	}
            	
            });
            
            //修改操作
            $('#edit_btn').click(function(){
            	var loginacct = $('#loginacct_input').val();
            	var username = $('#username_input').val();
            	var email = $('#email_input').val();
            	if(loginacct==""){
            		layer.msg('登录账号不能为空！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
            	if(loginacct.length<5){
            		layer.msg('登录账号长度过短！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
            	if(username==""){
            		layer.msg('用户名不能为空！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
            	if(username.length<2){
            		layer.msg('用户名字符长度过短！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
            	if(email==""){
            		layer.msg('邮箱不能为空！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
            	if(!regEmail.test(email)){
            		layer.msg('请检查邮箱格式！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;	
            	}
                //验证通过后进行保存
                $.ajax({
                	url:"${APP_PATH}/permission/user/editDo",
                	type:"POST",
                	data:{
                		"id":"${user.id}",
                		"loginacct":loginacct,
                		"username":username,
                		"email":email
                	},
                	success:function(result){
                		if(result.success){
                    		layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
                    			
                    		});
                    		window.location.href="${APP_PATH}/permission/user/index";
                		}else{
                    		layer.msg('修改失败！',{time:2000,icon:5,shift:5},function(){
                    			
                    		});
                		}
                	}
                });
            });
            
            //重置按钮初始化文本框数据
            $('#reset_btn').click(function(){
            	$('#info_loginacct').text("");
            	$('#info_username').text("");
            	$('#info_email').text("");
            	$('#loginacct_input').val("${user.loginacct}");
            	$('#username_input').val("${user.username}");
            	$('#email_input').val("${user.email}");
            });
        </script>
  </body>
</html>