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
<title>项目分类页面</title>
<%@ include file="/WEB-INF/commons/common-css.jsp"%>
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
				pageContext.setAttribute("header_info", "项目分类");
				//设置边侧栏当前页面的链接为高亮模式
				pageContext.setAttribute("currentUrl", "manager/type/index");
	%>
	<%@ include file="/WEB-INF/commons/common-header.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/commons/common-leftbar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										id="query_text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" id="query_btn" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;" onclick="deleteTypes()">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" id="add_type" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<form id="userForm">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="45" style="text-align: center">序号</th>
											<th width="40" style="text-align: center"><input
												type="checkbox" id="allSelBox"></th>
											<th style="text-align: center; width: 250px">分类名称</th>
											<th style="text-align: center">分类简介</th>
											<th width="100" style="text-align: center; width: 150px">操作</th>
										</tr>
									</thead>
									<tbody style="text-align: center" id="typeData">

									</tbody>

									<tfoot>
										<tr>
											<td colspan="6" align="center">
												<ul class="pagination"></ul>
											</td>
										</tr>

									</tfoot>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 添加弹出的模态框 -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" class="close" id="closeMe" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加分类</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">分类名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="name" class="form-control"
									placeholder="输入分类名称">
								<p class="help-block label label-warning" id="info_name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">分类简介：</label>
							<div class="col-sm-8">
								<textarea name="introduction" id="introduction" maxlength="30" class="form-control"
									placeholder="简介" style="height:120px"></textarea> 
								<p>最多可输入30个文字，已输入<em style="font-weight:bold;color:red;font-style:italic"><span id="text-num">0</span>&nbsp;/&nbsp;30：</em></p>	
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="closeMe2" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="add_btn" class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改弹出的模态框 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" id="closeMe3" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改分类</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">分类名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="edit-name" class="form-control"
									placeholder="输入分类名称">
								<p class="help-block label label-warning" id="edit_info_name"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">分类简介：</label>
							<div class="col-sm-8">
								<textarea name="introduction" id="edit-introduction" maxlength=30 class="form-control"
									placeholder="简介" style="height:120px"></textarea> 
									<p>最多可输入30个文字，已输入<em style="font-weight:bold;color:red;font-style:italic"><span id="edit-text-num">0</span>&nbsp;/&nbsp;30：</em></p>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="closeMe4" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="edit_btn" class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/commons/common-js.jsp"%>
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
			queryPaged(1);//查询第一页
			//实现模糊查询功能
			$('#query_btn').click(function() {
				//获取文本框的值
				var queryText = $('#query_text').val();
				if (queryText == "") {//如果为空串
					likeFlag = false;//关闭模糊查询
				} else {//否则开启模糊查询
					likeFlag = true;
				}
				queryPaged(1);
			});
			//实现复选框的全选和反选功能
			$('#allSelBox').click(function() {
				//获取表头复选框的状态
				var flg = this.checked;
				//将表格中的全部复选框状态保持与表头一致
				$('#typeData :checkbox').each(function() {
					this.checked = flg;
				});
			});
		});
		//异步分页查询
		function queryPaged(pageNum) {
			var loadingIndex = null;
			var jsonData = {
				"page" : pageNum,
				"pageSize" : 10
			};
			if (likeFlag == true) {
				jsonData.queryText = $('#query_text').val();
			}
			//ajax异步请求
			$
					.ajax({
						url : "${APP_PATH}/manager/type/list",
						type : "POST",
						data : jsonData,
						beforeSend : function() {
							loadingIndex = layer.msg("处理中", {
								icon : 16
							});
						},
						success : function(result) {
							layer.close(loadingIndex);
							if (result.success) {
								//局部刷新页面数据
								var tableContent = "";
								var pageContent = "";
								//从后台获取数据
								var typePage = result.data;
								var types = typePage.datas;
								//遍历对象
								$
										.each(
												types,
												function(i, type) {
													tableContent += '<tr>';
													tableContent += '<td>'
															+ (i + 1) + '</td>';
													tableContent += '<td><input type="checkbox" id="typeId" value="'+type.id+'"></td>';
													tableContent += '<td>'
															+ type.name
															+ '</td>';
													tableContent += '<td>'
															+ type.introduction
															+ '</td>';
													tableContent += '<td>';
													tableContent += '<button type="button" class="btn btn-primary btn-xs" onclick="edit('
															+ type.id
															+ ')"><i class=" glyphicon glyphicon-pencil"></i></button>&nbsp;&nbsp;&nbsp;';
													tableContent += '<button type="button" class="btn btn-danger btn-xs" onclick="remove('
															+ type.id
															+ ',\''
															+ type.name
															+ '\')"><i class=" glyphicon glyphicon-remove"></i></button>';
													tableContent += '</td>';
													tableContent += '</tr>';
												});
								//显示页码导航条
								//首页
								pageContent += '<li><a href="#" onclick="queryPaged(1)">首页</a></li>';
								//上一页
								if (pageNum == 1) {
									pageContent += '<li class="disabled"><a href="#">上一页</a></li>';
								}

								if (pageNum > 1) {
									pageContent += '<li><a href="#" onclick="queryPaged('
											+ (pageNum - 1) + ')">上一页</a></li>';
								}
								//显示页码数
								for (var i = 1; i <= typePage.maxPage; i++) {
									//添加当前页样式  		    
									if (i == pageNum) {
										pageContent += '<li class="active"><a href="#">'
												+ i + '</a></li>';
									} else {
										pageContent += '<li><a href="#" onclick="queryPaged('
												+ i + ')">' + i + '</a></li>';
									}
								}
								//下一页
								if (pageNum < typePage.maxPage) {
									pageContent += '<li><a href="#" onclick="queryPaged('
											+ (pageNum + 1) + ')">下一页</a></li>';
								} else {
									pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
								}
								//末页
								pageContent += '<li><a href="#" onclick="queryPaged('
										+ typePage.maxPage + ')">末页</a></li>';
								$('#typeData').html(tableContent);//添加查询主内容
								$('.pagination').html(pageContent);//添加页码导航条
							} else {
								layer.msg("查询失败！", {
									time : 2000,
									icon : 5,
									shift : 5
								}, function() {

								});
							}
						}
					});
		}
		
		//添加项目分类
		$('#add_type').click(function(){
			$('#name').val("");
			$('#introduction').val("");//清空文本框
			$('#info_name').text("");
			$('#name').parent().removeClass('has-error').addClass('has-success');
			//弹出模态框
			$('#addModal').modal('show');
			//校验并显示提示信息
			$('#name').blur(function(){
				$('#info_name').text("");
				$('#name').parent().removeClass('has-error').addClass('has-success');
				var name = $('#name').val();
				//先判断是否为空
				if(name==""){
					$('#info_name').text("分类名称不能为空！");
					$('#name').parent().addClass('has-error');
					return;
				}
				//然后判断名称是否重复
				$.ajax({
					url:'${APP_PATH}/manager/type/validateType',
					type:"POST",
					data:{
						"name":name
					},
					success:function(result){
						if(result.success){
							$('#info_name').text("");
							$('#name').parent().removeClass('has-error').addClass('has-success');
						}else{
							$('#info_name').text("分类名称已存在！");
							$('#name').parent().addClass('has-error');
						}
					}
				});
			});
		});
		
		$('#introduction').keyup(function(){
			var len = $('#introduction').val().length;
			if(len==0){
				$('#text-num').text(0);
			}
			if(len<=30){
				$('#text-num').text(len);
			}
		});
		
		//点击关闭时文字统计清0
		$('#closeMe').click(function(){
			$('#text-num').text(0);;
		});
		
		$('#closeMe2').click(function(){
			$('#text-num').text(0);;
		});
		
		$('#closeMe3').click(function(){
			$('#edit-text-num').text(0);;
		});
		
		$('#closeMe4').click(function(){
			$('#edit-text-num').text(0);;
		});
		//执行添加操作
		$('#add_btn').click(function(){
			var name = $('#name').val();
			var introduction = $('#introduction').val();
			if(name==""){
				layer.msg('分类名称不能为空！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
			$.ajax({
				url:'${APP_PATH}/manager/type/addType',
				type:"POST",
				data:{
					"name":name,
					"introduction":introduction
				},
				success:function(result){
					if(result.success){
						layer.msg('添加成功！',{time:2000,icon:6,shift:5},function(){
							
						});
						//关闭模态框
						$('#addModal').modal('hide');
						$('#text-num').text(0);
						queryPaged(1);
					}else{
						layer.msg(result.data,{time:2000,icon:5,shift:5},function(){
							
						});
					}
				}
			});
		});
		
		//修改分类信息
		var editId = null;
		function edit(id){
			$('#edit_info_name').text("");
			$('#edit-name').parent().removeClass('has-error').addClass('has-success');
			editId = id;
			//先进行信息回显
			$.ajax({
				url:'${APP_PATH}/manager/type/getType',
				type:"POST",
				data:{
					"id":id
				},				
				success:function(result){
					if(result.success){
						var type = result.data;
						$('#edit-name').val(type.name);
						$('#edit-introduction').val(type.introduction);//数据回显
						var len = $('#edit-introduction').val().length;
						$('#edit-text-num').text(len);
						$('#edit-introduction').keyup(function(){
							var newLen = $('#edit-introduction').val().length;
							if(newLen==0){
								$('#edit-text-num').text(0);
							}
							if(newLen<=30){
								$('#edit-text-num').text(newLen);
							}
						});
						$('#edit_info_name').text("");
						$('#edit-name').parent().removeClass('has-error').addClass('has-success');
						//弹出模态框
						$('#editModal').modal('show');
						//校验并显示提示信息
						$('#edit-name').blur(function(){
							$('#edit_info_name').text("");
							$('#edit-name').parent().removeClass('has-error').addClass('has-success');
							var name = $('#edit-name').val();
							//先判断是否为空
							if(name==""){
								$('#edit_info_name').text("分类名称不能为空！");
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
		
		//执行修改操作
		$('#edit_btn').click(function(){
			var name = $('#edit-name').val();
			var introduction = $('#edit-introduction').val();
			if(name==""){
				layer.msg('分类名称不能为空！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
			$.ajax({
				url:'${APP_PATH}/manager/type/editType',
				type:"POST",
				data:{
					"id":editId,
					"name":name,
					"introduction":introduction
				},
				success:function(result){
					if(result.success){
						layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
							
						});
						//关闭模态框
						$('#editModal').modal('hide');
						$('#edit-text-num').text(0);;
						queryPaged(1);
					}else{
						layer.msg('该分类已存在！',{time:2000,icon:5,shift:5},function(){
							
						});
					}
				}
			});
		});

		//单个删除
		function remove(id, name) {
			layer.confirm("删除分类【" + name + "】，是否继续？", {
				icon : 3,
				title : "提示"
			}, function(cindex) {
				//删除用户信息
				$.ajax({
					url : "${APP_PATH}/manager/type/delete",
					type : "POST",
					data : {
						"id" : id
					},
					success : function(result) {
						if (result.success) {
							layer.msg("删除成功！", {
								time : 2000,
								icon : 6,
								shift : 5
							}, function() {

							});
							queryPaged(1);//删除成功后刷新页面
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

		//批量删除
		function deleteTypes() {
			//获取选中的所有复选框
			var boxes = $('#typeData :checked');
			if (boxes.length == 0) {
				layer.msg("请勾选要删除的记录！", {
					time : 2000,
					icon : 5,
					shift : 5
				}, function() {

				});
				return;
			} else {
				var typeId = $('#typeId').val();//获取选中的所有id
				var ids = "";
				for (var i = 0; i < boxes.length; i++) {
					ids += boxes[i].defaultValue + ",";
				}
				layer.confirm("删除选中的" + boxes.length + "条用户信息，是否继续？", {
					icon : 3,
					title : "提示"
				}, function(cindex) {
					//删除用户信息
					$.ajax({
						url : "${APP_PATH}/manager/type/deleteTypes",
						type : "POST",
						data : {
							"ids" : ids
						},
						success : function(result) {
							if (result.success) {
								layer.msg("删除成功！", {
									time : 2000,
									icon : 6,
									shift : 5
								}, function() {

								});
								queryPaged(1);//删除成功重新查询第一页
							} else {//删除失败提示信息
								layer.msg("删除分类失败！", {
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
		}
	</script>
	<%@ include file="/WEB-INF/commons/same-js.jsp"%>
</body>
</html>
