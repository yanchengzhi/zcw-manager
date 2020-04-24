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
	<div class="modal fade" id="permissionModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">分配权限</h4>
				</div>
				<div class="modal-body">
					<ul id="permissionTree" class="ztree"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="doAssign()">分配权限</button>
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
							icoObj.removeClass("button ico_docu ico_open")
									.addClass(treeNode.icon).css("background",
											"");
						}
					},
					//鼠标悬停时，显示控件
					addHoverDom: function(treeId, treeNode){  
						var aObj = $("#" + treeNode.tId + "_a");
						if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
						var s = '<span id="btnGroup'+treeNode.tId+'">';
						if ( treeNode.level == 0 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('+treeNode.id+')" href="#" title="添加权限">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if ( treeNode.level == 1 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('+treeNode.id+')" href="#" title="添加权限">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="edit('+treeNode.id+')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							if (treeNode.children.length == 0) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('+treeNode.id+')" href="#" title="删除权限">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
						} else if ( treeNode.level == 2 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('+treeNode.id+')" href="#" title="添加权限">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="edit('+treeNode.id+')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('+treeNode.id+')" href="#" title="删除权限">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
						}
		
						s += '</span>';
						aObj.after(s);
					},
					//鼠标移出时，隐藏控件
					removeHoverDom: function(treeId, treeNode){
						$("#btnGroup"+treeNode.tId).remove();
					}
				}
			};

			//初始化树形结构数据
			$.fn.zTree.init($('#permissionTree'), setting);
		});



	</script>
	<%@ include file="/WEB-INF/commons/same-js.jsp"%>
</body>
</html>
