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
<title>广告管理页面</title>
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
input[type=checkbox] {
      width:15px;
      height:15px;        
   }
</style>
</head>
<body>
	<!-- 如果在jsp页面中写Java代码，尽量放在一处，还是建议jsp中少用Java代码 -->
	<%
	    //设置头部标题
				pageContext.setAttribute("header_info", "广告管理");
				//设置边侧栏当前页面的链接为高亮模式
				pageContext.setAttribute("currentUrl", "manager/advertisement/index");
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
							style="float: right; margin-left: 10px;" onclick="deleteAds()">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" id="add_adver" class="btn btn-primary"
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
											<th style="text-align: center; width: 550px">广告描述</th>
											<th style="text-align: center">审核状态</th>
											<th width="100" style="text-align: center; width: 150px">操作</th>
										</tr>
									</thead>
									<tbody style="text-align: center" id="adverData">

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
					<h4 class="modal-title" id="myModalLabel">添加广告</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="add-form">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">广告名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="name_input" class="form-control"
									placeholder="输入广告名称">
								<p class="help-block label label-warning" id="info_name" style="font-size:14px"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">选择广告：</label>
							<div class="col-sm-8">
								<input type="file" name="file" id="file_input"/>
							</div>
						</div>
						<!-- 图片的展示位 -->
						<div class="form-group">
						   <label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">广告预览：</label>
				            <div class="col-sm-8" id="img-show">
								
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
	
	<!-- 修改弹出的模态框 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 650px">
				<div class="modal-header">
					<button type="button" class="close" id="closeMe" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改广告</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="edit-form">
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px">广告名称：</label>
							<div class="col-sm-8">
								<input type="text" name="name" id="edit_name_input" class="form-control"
									placeholder="输入广告名称">
								<p class="help-block label label-warning" id="edit_info_name" style="font-size:14px"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">选择广告：</label>
							<div class="col-sm-8">	
							    <!-- 隐藏的文件文本框 -->						   
								<input type="file" name="edit-file" id="edit_file_input" style="display:none"/>	
								<!-- 这个a标签只用来作显示，模拟file文本框 -->							
								<a href="#" class="btn btn-default" id="openInput">选择文件</a>&nbsp;&nbsp;<span id="show-file"></span>
							</div>
						</div>
						<!-- 图片的展示位 -->
						<div class="form-group">
						   <label class="col-sm-2 control-label" style="margin-left:25px;margin-right:-15px;margin-top:-7px;">广告预览：</label>
				            <div class="col-sm-8" id="edit-img-show">
								
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
				$('#adverData :checkbox').each(function() {
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
						url : "${APP_PATH}/manager/advertisement/list",
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
								var adPage = result.data;
								var ads = adPage.datas;
								//遍历对象
								$
										.each(
												ads,
												function(i, ad) {
													tableContent += '<tr>';
													tableContent += '<td>'
															+ (i + 1) + '</td>';
													tableContent += '<td><input type="checkbox" id="adverId" value="'+ad.id+'"></td>';
													tableContent += '<td>'
															+ ad.name
															+ '</td>';
													tableContent += '<td>'
															+ ad.status
															+ '</td>';
													tableContent += '<td>';
													tableContent += '<button type="button" class="btn btn-success btn-xs" url="'+ad.url+'" id="showImg"><i class="glyphicon glyphicon-eye-open"></i></button>&nbsp;&nbsp;&nbsp;';													
													tableContent += '<button type="button" class="btn btn-primary btn-xs" onclick="edit('
															+ ad.id
															+ ')"><i class=" glyphicon glyphicon-pencil"></i></button>&nbsp;&nbsp;&nbsp;';
													tableContent += '<button type="button" class="btn btn-danger btn-xs" onclick="remove('
															+ ad.id
															+ ',\''
															+ ad.name
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
								for (var i = 1; i <= adPage.maxPage; i++) {
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
								if (pageNum < adPage.maxPage) {
									pageContent += '<li><a href="#" onclick="queryPaged('
											+ (pageNum + 1) + ')">下一页</a></li>';
								} else {
									pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
								}
								//末页
								pageContent += '<li><a href="#" onclick="queryPaged('
										+ adPage.maxPage + ')">末页</a></li>';
								$('#adverData').html(tableContent);//添加查询主内容
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
		
		//点击a标签时，弹出文本选择框
		$('#openInput').click(function(){
			$('#edit_file_input').click();
		});
		
		//为文件选择项绑定change事件
		$('#file_input').change(function(event){
			//获取选中的所有文件
			var files = event.target.files;
			var file;//声明
			if(files.length>0){
				file = files[0];//获取选中的单个文件
				//图片格式的校验
				var reg = /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/; 
				if(!reg.test(file.name)){
					layer.msg('请选择图片格式！',{time:2000,icon:0,shift:5},function(){
						
					});
					$('#file_input').val("");
					return;
			}
			}
			//获取URL对象
			var URL = window.URL || window.webkitURL;
			//创建图片的临时URL地址
			var imgURL = URL.createObjectURL(file);
			//图片展示
			$('#img-show').empty();
			$('#img-show').append('<img src="'+imgURL+'" style="width:400px;height:300px">');
		});
		
		//添加项目分类
		$('#add_adver').click(function(){
			$('#img-show').empty();
			$('#name_input').val("");
			$('#file_input').val("");
			$('#info_name').text("");
			$('#name_input').parent().removeClass('has-error').addClass('has-success');
			//弹出模态框
			$('#addModal').modal('show');
			//校验并显示提示信息
			$('#name_input').blur(function(){
				$('#info_name').text("");
				$('#name_input').parent().removeClass('has-error').addClass('has-success');
				var name = $('#name_input').val();
				//先判断是否为空
				if(name==""){
					$('#info_name').text("广告名称不能为空！");
					$('#name_input').parent().addClass('has-error');
					return;
				}
				//然后判断名称是否重复
				$.ajax({
					url:'${APP_PATH}/manager/advertisement/validateAdName',
					type:"POST",
					data:{
						"name":name
					},
					success:function(result){
						if(result.success){
							$('#info_name').text("");
							$('#name_input').parent().removeClass('has-error').addClass('has-success');
						}else{
							$('#info_name').text("该广告已存在！");
							$('#name_input').parent().addClass('has-error');
						}
					}
				});
			});
		});
		
		
		//执行添加操作
		$('#add_btn').click(function(){
			var name = $('#name_input').val();
            //获取选取的全部文件对象
            var files = $('input[name="file"]')[0].files;
			if(name==""){
				layer.msg('广告名称不能为空！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
			if(files.length==0){
				layer.msg('请选择要上传的广告！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
			//获取单个文件
			var file = files[0];
			if(file.size>3145728){
				layer.msg('图片大小不能超过3M！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
            //验证后提交表单
            //$('#add-form').submit();
            var data = new FormData();
            //可以选择要添加的元素，提交部分数据
            data.append("name",name);
            data.append("file",file);
            //使用Ajax提交表单
            $.ajax({
            	url:"${APP_PATH}/manager/advertisement/upload",
            	type:"POST",
            	data:data,
            	//data:new FormData($('#add-form')[0]),
            	processData:false,//声明不处理和编码这些数据
            	contentType:false,//声明不使用默认的内容类型
            	success:function(result){
            		if(result.success){
        				layer.msg('上传成功！',{time:2000,icon:6,shift:5},function(){
        					
        				});
        	            //关闭模态框
        	            $('#addModal').modal('hide');
        	            //刷新页面
        	            queryPaged(1);
            		}else{
        				layer.msg(result.data,{time:2000,icon:5,shift:5},function(){
        					
        				});
            		}
            	}
            });
		});
		
		//点击查看图片
		$('body').on("click","#showImg",function(){
			var url = $(this).attr("url")//获取当前对象的url属性值
			layer.open({
				type:1,
				skin:'layui-layer-rim',//加上边框
				area:['800px','550px'],//宽高
				content:'<img src="${APP_PATH}/'+url+'" style="width:770px;height:495px"/>'
			});
		});
		
		$('#edit_file_input').change(function(event){
			//获取选中的所有文件
			var files = event.target.files;
			var file = files[0];
			//动态改变a标签后面显示的图片名称
			$('#show-file').text(file.name);
			//获取URL对象
			var URL = window.URL || window.webkitURL;
			//创建图片的临时URL地址
			var imgURL = URL.createObjectURL(file);
			//图片展示
			$('#edit-img-show').empty();
			$('#edit-img-show').append('<img src="'+imgURL+'" style="width:400px;height:300px">');
		});
		
		//修改分类信息
		var editId = null;
		function edit(id){
			$('#edit-img-show').empty();
			$('#edit_info_name').text("");
			$('#edit_name_input').parent().removeClass('has-error').addClass('has-success');
			editId = id;
			//先进行信息回显
			$.ajax({
				url:'${APP_PATH}/manager/advertisement/getAd',
				type:"POST",
				data:{
					"id":id
				},				
				success:function(result){
					if(result.success){
						var ad = result.data;
						$('#edit_name_input').val(ad.name);
						$('#edit_info_name').text("");
						$('#edit_name_input').parent().removeClass('has-error').addClass('has-success');
						$('#show-file').text(ad.url.substring(4));
						//图片回显
						$('#edit-img-show').append('<img src="${APP_PATH}/'+ad.url+'" style="width:400px;height:300px">');
						//弹出模态框
						$('#editModal').modal('show');
						//校验并显示提示信息
						$('#edit_name_input').blur(function(){
							$('#edit_info_name').text("");
							$('#edit_name_input').parent().removeClass('has-error').addClass('has-success');
							var name = $('#edit_name_input').val();
							//先判断是否为空
							if(name==""){
								$('#edit_info_name').text("广告名称不能为空！");
								$('#edit_name_input').parent().addClass('has-error');
								return;
							}
							$('#edit_info_name').text("");
							$('#edit_name_input').parent().removeClass('has-error').addClass('has-success');
						});
					}
				}
			});	
		}
		
		//执行修改操作
		$('#edit_btn').click(function(){
			var name = $('#edit_name_input').val();
			if(name==""){
				layer.msg('广告名称不能为空！',{time:2000,icon:0,shift:5},function(){
					
				});
				return;
			}
			var files = $('input[name="edit-file"]')[0].files;
			if(files.length==0){//说明没选，也就是图片不变，只改广告名
				$.ajax({
					url:'${APP_PATH}/manager/advertisement/editAd',
					type:"POST",
					data:{
						"id":editId,
						"name":name
					},
					success:function(result){
						if(result.success){
							layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
								
							});
							//关闭模态框
							$('#editModal').modal('hide');
							//刷新页面
							queryPaged(1);
						}else{
							layer.msg('该广告已存在！',{time:2000,icon:5,shift:5},function(){
								
							});
						}
					}
				});
			}else{
				var file = files[0];
				var data = new FormData();
				data.append("id",editId);
				data.append("name",name);
				data.append("file",file);
				console.log(data);
				$.ajax({
					url:'${APP_PATH}/manager/advertisement/editAd',
					type:"POST",
					data:data,
					processData:false,
					contentType:false,
					success:function(result){
						if(result.success){
							layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
								
							});
							//关闭模态框
							$('#editModal').modal('hide');
							queryPaged(1);
						}else{
							layer.msg('该广告已存在！',{time:2000,icon:5,shift:5},function(){
								
							});
						}
					}
				});
			}
			
		});

		//单个删除
		function remove(id, name) {
			layer.confirm("删除广告【" + name + "】，是否继续？", {
				icon : 3,
				title : "提示"
			}, function(cindex) {
				//删除用户信息
				$.ajax({
					url : "${APP_PATH}/manager/advertisement/delete",
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
		function deleteAds() {
			//获取选中的所有复选框
			var boxes = $('#adverData :checked');
			if (boxes.length == 0) {
				layer.msg("请勾选要删除的记录！", {
					time : 2000,
					icon : 5,
					shift : 5
				}, function() {

				});
				return;
			} else {
				var adverId = $('#adverId').val();//获取选中的所有id
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
						url : "${APP_PATH}/manager/advertisement/deleteAds",
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
