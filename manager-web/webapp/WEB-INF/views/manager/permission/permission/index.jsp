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
<title>许可页面</title>
<%@ include file="/WEB-INF/commons/common-css.jsp"%>
<!-- zTree需要的样式表 -->
<link rel="stylesheet" href="${APP_PATH}/static/ztree/zTreeStyle.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/main.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>
<body>
	<!-- 如果在jsp页面中写Java代码，尽量放在一处，还是建议jsp中少用Java代码 -->
	<%
	    //设置头部标题
				pageContext.setAttribute("header_info", "许可维护");
				//设置边侧栏当前页面的链接为高亮模式
				pageContext.setAttribute("currentUrl", "permission/permission/index");
	%>
	<%@ include file="/WEB-INF/commons/common-header.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/commons/common-leftbar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						</h3>
					</div>
					<div class="panel-body">
						<ul id="permissionTree" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 添加节点的模态框 -->
	<div class="modal fade" id="addNodeModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加菜单</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-top:8px;margin-right:-15px;margin-left:25px">父级菜单：</label>
							<div class="col-sm-8">
								<div class="checkbox">
									<select name="parentMenu" id="parentMenu" class="form-control">
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">菜单名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="name" class="form-control"
									placeholder="输入菜单名称">
								<p class="help-block label label-warning" id="info_name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">菜单URL：</label>
							<div class="col-sm-8">
								<input type="text" name="url" id="pUrl" class="form-control"
									placeholder="输入菜单URL"> 
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="add_btn" class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改节点的模态框 -->
	<div class="modal fade" id="editNodeModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true" id="edit-close">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改菜单</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-top:8px;margin-right:-15px;margin-left:25px">父级菜单：</label>
							<div class="col-sm-8">
								<div class="checkbox">
									<select name="parentMenu" id="edit-parentMenu" class="form-control">
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">菜单名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="edit-name" class="form-control"
									placeholder="输入菜单名称">
								<p class="help-block label label-warning" id="edit_info_name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">菜单URL：</label>
							<div class="col-sm-8">
								<input type="text" name="url" id="edit_pUrl" class="form-control"
									placeholder="输入菜单URL"> 
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="edit-close" data-dismiss="modal">关闭</button>
					<button type="button" id="edit_btn" class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/commons/common-js.jsp"%>
	<script src="${APP_PATH}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
	<script type="text/javascript">
		var likeFlag = false;//模糊查询是否开启的标志
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});

			//页面加载完成后初始化树形结构
			var setting = {
				//异步请求数据
				async : {
					url : "${APP_PATH}/permission/permission/loadAllData",
					enable : true,
					autoParam : [ "id", "name=n", "level=1v" ]
				},
				//禁用url的跳转功能，用data容器包裹一个key，在key里将链接的属性名称改为表中不存在的
				//注意，直接使用key是不生效的，必选要嵌套在data里
				data : {
					key : {
						url : "xxurl"
					}
				},
				view : {
					//不支持多选
					selectedMulti : false,
					//改变节点图标
					addDiyDom : function(treeId, treeNode) {
						var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
						if (treeNode.icon) {
							icoObj.removeClass("button ico_docu ico_open");//移除默认图标
							icoObj.before('<span class="'+treeNode.icon+'"></span>')
						}
					},
					//鼠标悬停时，显示控件
					addHoverDom : function(treeId, treeNode) {
						var aObj = $("#" + treeNode.tId + "_a");
						if (treeNode.editNameFlag
								|| $("#btnGroup" + treeNode.tId).length > 0)
							return;
						var s = '<span id="btnGroup'+treeNode.tId+'">';
						if (treeNode.level == 0) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('
									+ treeNode.id
									+ ')" href="#" title="添加权限">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if (treeNode.level == 1) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('
									+ treeNode.id
									+ ')" href="#" title="添加权限">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="edit('
									+ treeNode.pid + ','+treeNode.id+',\''+treeNode.name+'\''+',\''+treeNode.url+'\')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							if (treeNode.children.length == 0) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('
										+ treeNode.id
										+',\''+treeNode.name+'\')" href="#" title="删除权限">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
						} else if (treeNode.level == 2) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="edit('
									+ treeNode.pid + ','+treeNode.id+',\''+treeNode.name+'\''+',\''+treeNode.url+'\')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('
									+ treeNode.id
									+',\''+treeNode.name+'\')" href="#" title="删除权限">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
						}

						s += '</span>';
						aObj.after(s);
					},
					//鼠标移出时，隐藏控件
					removeHoverDom : function(treeId, treeNode) {
						$("#btnGroup" + treeNode.tId).remove();
					}
				}
			};

			//初始化树形结构数据
			$.fn.zTree.init($('#permissionTree'), setting);
		});

		//添加节点
		function addNode(id) {
			$('#info_name').text("");
			$('#name').parent().removeClass('has-error').addClass('has-success');
			//弹出模态框
			$('#addNodeModal').modal('show');
			//查出父菜单
			$.ajax({
				url:"${APP_PATH}/permission/permission/getParentMenu",
				type:"POST",
				data:{
					"id":id
				},
				success:function(result){
					if(result.success){
						var parent = result.data;//获取后端传过来的数据
						$('#parentMenu option').remove();//每次添加option前移除以前的所有option
						//给下拉框动态设置值，且不允许改变
						$('#parentMenu').append('<option id="pMenu" value="'+parent.id+'">'+parent.name+'</option>');
					}
				}
			});
			
			//校验显示提示信息
			$('#name').blur(function(){
				$('#info_name').text("");
				$('#name').parent().removeClass('has-error').addClass('has-success');
				var name = $('#name').val();
				//先判断是否为空
				if(name==""){
					$('#info_name').text("菜单名称不能为空！");
					$('#name').parent().addClass('has-error');
					return;
				}
				//然后判断名称是否重复
				$.ajax({
					url:'${APP_PATH}/permission/permission/validateMenu',
					type:"POST",
					data:{
						"name":name
					},
					success:function(result){
						if(result.success){
							$('#info_name').text("");
							$('#name').parent().removeClass('has-error').addClass('has-success');
						}else{
							$('#info_name').text("菜单名称已存在！");
							$('#name').parent().addClass('has-error');
						}
					}
				});
			});
			
			//执行添加操作
			$('#add_btn').click(function(){
				var pId = $('#parentMenu option:selected').val();
				var name = $('#name').val();
				var pUrl = $('#pUrl').val();
				if(name==""){
            		layer.msg('菜单名称不能为空！',{time:2000,icon:0,shift:5},function(){
            			
            		});
            		return;
				}
				$.ajax({
					url:"${APP_PATH}/permission/permission/addPermission",
					type:"POST",
					data:{
						"pid":pId,
						"name":name,
						"url":pUrl
					},
					success:function(result){
						if(result.success){
                    		layer.msg('添加成功！',{time:2000,icon:6,shift:5},function(){
                    			
                    		});
                    		$('#addNodeModal').modal('hide');
                    		window.location.href="${APP_PATH}/permission/permission/index";
						}else{
		            		layer.msg(result.data,{time:2000,icon:5,shift:5},function(){
		            			
		            		});
						}
					}
				});
			});
			
		}
		
		//修改节点
		function edit(pid,id,name,url){
			$('#edit_info_name').text("");
			$('#edit-name').parent().removeClass('has-error').addClass('has-success');
			//弹出模态框
		if(pid==1){//如果是一级子菜单，它的父级菜单固定不可选
		   $('#editNodeModal').modal('show');
			//进行数据回显
			$.ajax({
				url:"${APP_PATH}/permission/permission/getParentMenu",
				type:"POST",
				data:{
					"id":pid
				},
				success:function(result){
					if(result.success){
						var parent = result.data;
						//回显数据
						$('#edit-name').val("");
						$('#edit_pUrl').val("");
						$('#edit-parentMenu option').remove();//先清空
						$('#edit-parentMenu').append('<option value="'+parent.id+'" id="sOption">'+parent.name+'</option>');
					    $('#edit-name').val(name);
						$('#edit_pUrl').val(url);
						
						//校验显示提示信息
						$('#edit-name').blur(function(){
							var pName = $('#edit-name').val();
							//先判断是否为空
							if(pName==""){
								$('#edit_info_name').text("菜单名称不能为空！");
								$('#edit-name').parent().addClass('has-error');
								return;
							}
							$('#edit_info_name').text("");
							$('#edit-name').parent().removeClass('has-error').addClass('has-success');
						});
						
					}
				}
			});
		}else{//如果是二级子菜单，它的父菜单可变
			$('#edit_info_name').text("");
			$('#edit-name').parent().removeClass('has-error').addClass('has-success');
			$('#editNodeModal').modal('show');
			$('#edit-parentMenu option').remove();
			$('#edit-name').val("");
			$('#edit_pUrl').val("");//先清空
			$.ajax({
				url:"${APP_PATH}/permission/permission/getTwoMenus",
				type:"POST",
				success:function(result){
					if(result.success){
						var pers = result.data;
						$.each(pers,function(i,per){
							if(per.id==pid){
								$('#edit-parentMenu').append('<option value="'+per.id+'" selected>'+per.name+'</option>');
							}else{
								$('#edit-parentMenu').append('<option value="'+per.id+'">'+per.name+'</option>');
							}
						});
						$('#edit-name').val(name);
					    $('#edit_pUrl').val(url);//再回显
					    ;
						//校验显示提示信息
						$('#edit-name').blur(function(){
							var pName = $('#edit-name').val();
							//先判断是否为空
							if(pName==""){
								console.log(pName);
								$('#edit_info_name').text("菜单名称不能为空！");
								$('#edit-name').parent().addClass('has-error');
								return;
							}
							$('#edit_info_name').text("");
							$('#edit-name').parent().removeClass('has-error').addClass('has-success');
						});
					}
				}
			});
		}
		
	}
		
    
		
	//执行修改保存操作
	$('#edit_btn').click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
		var nodes = treeObj.getSelectedNodes();//获取选中的节点
		var id = nodes[0].id;
		var pId = $('#edit-parentMenu option:selected').val();
		var name = $('#edit-name').val();
		var pUrl = $('#edit_pUrl').val();
		if(name==""){
    		layer.msg('菜单名称不能为空！',{time:2000,icon:0,shift:5},function(){
    			
    		});
    		return;
		}
		//执行保存
		$.ajax({
			url:"${APP_PATH}/permission/permission/editPermission",
			type:"POST",
			data:{
				"id":id,
				"pid":pId,
				"name":name,
				"url":pUrl
			},
			success:function(result){
				if(result.success){
					layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
						
					});
					$('#editNodeModal').modal('hide');//隐藏模态框
					//刷新页面
					window.location.href="${APP_PATH}/permission/permission/index";
				}else{
					layer.msg(result.data,{time:2000,icon:5,shift:5},function(){
						
					});
				}
			}
		});
	});
	
	//删除节点
	function deleteNode(id,name){
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
		var nodes = treeObj.getSelectedNodes();//获取选中的节点
		var id = nodes[0].id;
		layer.confirm("删除节点【" + name + "】，是否继续？", {
			icon : 3,
			title : "提示"
		}, function(cindex) {
			//删除用户信息
			$.ajax({
				url : "${APP_PATH}/permission/permission/deleteNode",
				type : "POST",
				data : {
					"id" : id
				},
				success : function(result) {
					if (result.success) {
						layer.msg("删除成功！", {time:2000,icon:6,shift:5}, function() {

						});
						//刷新页面
						window.location.href="${APP_PATH}/permission/permission/index";
					} else {//删除失败提示信息
						layer.msg('删除失败！', {
							time : 2000,
							icon : 5,
							shift : 5
						}, function() {

						});
					}
				}
			});
			layer.close(cindex);
		}, function(cindex) {
			layer.close(cindex);
		});
	}
	</script>
	<%@ include file="/WEB-INF/commons/same-js.jsp"%>
</body>
</html>
