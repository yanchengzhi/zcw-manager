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
    <title>用户页面</title>
    <%@ include file="/WEB-INF/commons/common-css.jsp" %>
	<link rel="stylesheet" href="${APP_PATH}/static/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>
    <%@ include file="/WEB-INF/commons/common-header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="/WEB-INF/commons/common-leftbar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" id="query_text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" id="query_btn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr>
                  <th width="45" style="text-align: center">序号</th>
				  <th width="40" style="text-align: center"><input type="checkbox"></th>
                  <th style="text-align: center">账号</th>
                  <th style="text-align: center">名称</th>
                  <th style="text-align: center">邮箱地址</th>
                  <th width="100" style="text-align: center">操作</th>
                </tr>
              </thead>
              <tbody style="text-align: center" id="userData">

              </tbody>
              
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination"></ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
    <script type="text/javascript">
    var likeFlag = false;//模糊查询是否开启的标志
            $(function(){
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
              });
            $("tbody .btn-success").click(function(){
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function(){
                window.location.href = "edit.html";
            });
            
            //异步分页查询
            function queryPaged(pageNum){
            	var loadingIndex = null;
            	var jsonData = {
            			"page":pageNum,
            			"pageSize":10
            	};
    			if (likeFlag == true) {
    				jsonData.queryText = $('#query_text').val();
    			}
    			//ajax异步请求
    			$.ajax({
    				url:"${APP_PATH}/permission/user/list",
    				type:"POST",
    				data:jsonData,
    				beforeSend:function(){
						loadingIndex = layer.msg("处理中", {
							icon : 16
						});
    				},
    				success:function(result){
    					layer.close(loadingIndex);
    					if(result.success){
							//局部刷新页面数据
							var tableContent = "";
							var pageContent = "";
							//从后台获取数据
							var userPage = result.data;
							var users = userPage.datas;
							//遍历对象
							$.each(users,function(i,user){
								tableContent+='<tr>';
				                tableContent+='<td>'+(i+1)+'</td>';
				                tableContent+='<td><input type="checkbox"></td>';
				                tableContent+='<td>'+user.loginacct+'</td>';
				                tableContent+='<td>'+user.username+'</td>';
				                tableContent+='<td>'+user.email+'</td>';
				                tableContent+='<td>';
				                tableContent+='<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>&nbsp;';
				                tableContent+='<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>&nbsp;';
				                tableContent+='<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
						        tableContent+='</td>';
							    tableContent+='</tr>';
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
							for (var i = 1; i <= userPage.maxPage; i++) {
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
							if (pageNum < userPage.maxPage) {
								pageContent += '<li><a href="#" onclick="queryPaged('
										+ (pageNum + 1) + ')">下一页</a></li>';
							} else {
								pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
							}
							//末页
							pageContent += '<li><a href="#" onclick="queryPaged('+userPage.maxPage+')">末页</a></li>';
							$('#userData').html(tableContent);//添加查询主内容
							$('.pagination').html(pageContent);//添加页码导航条
    					}else{
							layer.msg("查询失败！", {time:2000,icon:5,shift:6}, function() {

							});
    					}
    				}
    			});
            }
        </script>
  </body>
</html>
    