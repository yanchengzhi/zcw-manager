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
  <%
  pageContext.setAttribute("header_info", "用户角色分配"); 
  pageContext.setAttribute("currentUrl", "permission/user/index");
  %>
  <body>
  <%@ include file="/WEB-INF/commons/common-header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="/WEB-INF/commons/common-leftbar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form role="form" id="roleForm" class="form-inline">
				  <!-- 这个文本框隐藏，用于获取用户ID传值用 -->
			      <input type="hidden" name="userid" value="${user.id}">
				  <div class="form-group">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select id="leftList" name="assignIds" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                        <c:forEach items="${unAssignRoles}" var="role">
                           <option value="${role.id}">${role.name}</option>
                        </c:forEach>
                    </select>
				  </div>
				  <div class="form-group">
                        <ul>
                            <li id="ltrBtn" class="btn btn-default glyphicon glyphicon-chevron-right" ></li>
                            <br>
                            <li id="rtlBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select id="rightList" name="unAssignIds" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                        <c:forEach items="${assignRoles}" var="role">
                           <option value="${role.id}">${role.name}</option>
                        </c:forEach>
                    </select>
				  </div>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp"%>
    <script src="${APP_PATH}/static/layer/layer.js"></script>
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
			    
			    //实现角色从左框向右框移动
			    $('#ltrBtn').click(function(){
			    	//获取左边选中的列表项
			    	var options = $('#leftList :selected');
			    	if(options.length==0){
			    		layer.msg('请选择要分配的角色！',{time:2000,icon:5,shift:5},function(){
			    			
			    		});
			    		return;
			    	}
			    	//分配角色
			    	$.ajax({
			    		url:"${APP_PATH}/permission/user/assignRoles",
			    		type:"post",
			    		//序列化表单
			    		data:$('#roleForm').serialize(),
			    		success:function(result){
			    			if(result.success){
			    				//将选中的角色移到右边
			    				$('#rightList').append(options);
					    		layer.msg('角色分配成功！',{time:2000,icon:6,shift:5},function(){
					    			
					    		});
			    			}else{
					    		layer.msg('角色分配失败！',{time:2000,icon:5,shift:5},function(){
					    			
					    		});
			    			}
			    		}
			    	});
			    });
			    
			    //实现角色从右边框移动到左边框
			    $('#rtlBtn').click(function(){
			    	//获取右边选中的列表项
			    	var options = $('#rightList :selected');
			    	if(options.length==0){
			    		layer.msg('请选择要取消的角色！',{time:2000,icon:5,shift:5},function(){
			    			
			    		});
			    		return;
			    	}
			    	//分配角色
			    	$.ajax({
			    		url:"${APP_PATH}/permission/user/cancelRoles",
			    		type:"post",
			    		//序列化表单
			    		data:$('#roleForm').serialize(),
			    		success:function(result){
			    			if(result.success){
			    				//将选中的角色移到左边
			    				$('#leftList').append(options);
					    		layer.msg('取消角色成功',{time:2000,icon:6,shift:5},function(){
					    			
					    		});
			    			}else{
					    		layer.msg('取消角色失败！',{time:2000,icon:5,shift:5},function(){
					    			
					    		});
			    			}
			    		}
			    	});
			    });
            });
        </script>
        <%@ include file="/WEB-INF/commons/same-js.jsp" %>
  </body>
</html>
    