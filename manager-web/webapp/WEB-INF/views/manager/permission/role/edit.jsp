<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>用户添加</title>
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
  <%pageContext.setAttribute("header_info", "角色维护"); %>
  <body>
   <%@ include file="/WEB-INF/commons/common-header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="/WEB-INF/commons/common-leftbar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">新增</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				  <div class="form-group">
					<label for="exampleInputPassword1">角色名称</label>
					<input type="text" class="form-control" name="name" id="name_input" value="${role.name}" placeholder="请输入角色名称">
				    <p class="help-block label label-warning" id="info_name"></p>
				  </div>
				  <button type="button" class="btn btn-success" id="edit_btn"><i class="glyphicon glyphicon-plus"></i> 修改</button>
				  <button type="button" class="btn btn-danger" id="reset_btn"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
            $('#name_input').blur(function(){
                var name = $('#name_input').val();
            	if(name==""){
            		$('#info_name').text("角色名称不能为空！");
            		//文本框添加样式
            		$('#name_input').parent().addClass('has-error');
            		return;
            	}
            	//清空提示信息
            	$('#info_name').text("");
            	//文本框换样式
            	$('#name_input').parent().removeClass('has-error').addClass('has-success');
            });
            
            //修改操作
            $('#edit_btn').click(function(){
            	var name = $('#name_input').val();
            	if(name==""){
            		layer.msg('角色名称不能为空！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
            	}
                //验证通过后进行修改
                $.ajax({
                	url:"${APP_PATH}/permission/role/editRole",
                	type:"POST",
                	data:{
                		"id":"${role.id}",
                		"name":name
                	},
                	success:function(result){
                		if(result.success){
                    		layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
                    			
                    		});
                    		window.location.href="${APP_PATH}/permission/role/index";
                		}else{
                    		layer.msg('修改失败！',{time:2000,icon:5,shift:5},function(){
                    			
                    		});
                		}
                	}
                });
            });
            
            //重置按钮还原默认值
            $('#reset_btn').click(function(){
            	$('#info_name').text("");
            	$('#name_input').val("${role.name}");
            });
                   
        </script>
  </body>
</html>