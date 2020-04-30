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
  pageContext.setAttribute("header_info", "分类管理"); 
  //设置边侧栏当前页面的链接为高亮模式
  pageContext.setAttribute("currentUrl", "manager/classifymanager/index");
  %>
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
          <div class="table-responsive">
          <form id="userForm">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th width="45" style="text-align: center">序号</th>
                  <th style="text-align: center;width:330px">名称</th>
                  <th style="text-align: center;width:150px">商业公司</th>
                  <th style="text-align: center;width:150px">个体工商户</th>
                  <th style="text-align: center;width:150px">个人经营</th>
                  <th style="text-align: center;width:200px">政府及非营利组织</th>
                </tr>
              </thead>
              <tbody style="text-align: center" id="certData">

              </tbody>
              
			  <tfoot>
			     <tr >
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
    <%@ include file="/WEB-INF/commons/common-js.jsp" %>
    <script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
    <script type="text/javascript">
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
			    queryAll();
              });
            
            //异步分页查询
            function queryAll(){
    			//ajax异步请求
    			$.ajax({
    				url:"${APP_PATH}/manager/classifymanager/list",
    				type:"POST",
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
							//从后台获取数据
							var certs = result.data;
							console.log(certs);
							//遍历对象
							$.each(certs,function(i,cert){
								tableContent+='<tr>';
				                tableContent+='<td>'+(i+1)+'</td>';
				                tableContent+='<td>'+cert.name+'</td>';
				                tableContent+='<td><input type="checkbox" id="userId" value="'+cert.id+'"></td>';
				                tableContent+='<td><input type="checkbox" id="userId" value="'+cert.id+'"></td>';
				                tableContent+='<td><input type="checkbox" id="userId" value="'+cert.id+'"></td>';
				                tableContent+='<td><input type="checkbox" id="userId" value="'+cert.id+'"></td>';
							    tableContent+='</tr>';
							});
							$('#certData').html(tableContent);//添加查询主内容
    					}else{
							layer.msg("查询失败！", {time:2000,icon:5,shift:5}, function() {

							});
    					}
    				}
    			});
            }
            
        </script>  
      <%@ include file="/WEB-INF/commons/same-js.jsp" %>
  </body>
</html>
    