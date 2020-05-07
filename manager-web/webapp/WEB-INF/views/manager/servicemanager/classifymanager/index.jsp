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
            <table class="table table-bordered table-hover">
              <thead>
                <tr>
                  <th width="45" style="text-align: center">序号</th>
                  <th style="text-align: center;width:330px">名称</th>
                  <c:forEach items="${bTypes}" var="type">
                     <th style="text-align: center;width:150px">${type.name}</th>
                  </c:forEach>
                </tr>
              </thead>
              <tbody style="text-align: center">
                  <c:forEach items="${certs}" var="cert" varStatus="sta">
                    <tr>
                      <td>${sta.index+1}</td>
                      <td>${cert.name}</td>
                         <c:forEach items="${bTypes}" var="type" varStatus="sta2">
                         <td>
                         <input type="checkbox" class="checkMe" cid="${cert.id}" tname="${type.name}" ${relations[sta.index][sta2.index]==true?"checked":""}/>
                         </td>
                         </c:forEach>
                    </tr>
                  </c:forEach>
              </tbody>
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
			    
              });
            
            $('.checkMe').click(function(){
               var tname = $(this).attr('tname');//获取商户类型名称
               var cid = $(this).attr('cid');//获取资质id
             //获取复选框的状态，false为未选取，true为选取
               var flag = $(this).is(':checked');
                if(flag){//如果点击后复选框的状态为勾选
                	//发送ajax请求给后台添加
                	$.ajax({
                		url:"${APP_PATH}/manager/classifymanager/addBusinessCert",
                		type:"POST",
                		data:{
                			"accttype":tname,
                			"certid":cid
                		},
                		success:function(result){
                			if(result.success){
                				layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
                					
                				});
                				//刷新页面
                				window.location.href="${APP_PATH}/manager/classifymanager/index";
                			}else{
                				layer.msg('修改失败！',{time:2000,icon:5,shift:5},function(){
                					
                				});
                			}
                		}
                	});
                }else{//如果点击后为未勾选的，发请求给后台删除
                	$.ajax({
                		url:"${APP_PATH}/manager/classifymanager/removeBusinessCert",
                		type:"POST",
                		data:{
                			"accttype":tname,
                			"certid":cid
                		},
                		success:function(result){
                			if(result.success){
                				layer.msg('修改成功！',{time:2000,icon:6,shift:5},function(){
                					
                				});
                				//刷新页面
                				window.location.href="${APP_PATH}/manager/classifymanager/index";
                			}else{
                				layer.msg('修改失败！',{time:2000,icon:5,shift:5},function(){
                					
                				});
                			}
                		}
                	});
                }
            });
                    
        </script>  
      <%@ include file="/WEB-INF/commons/same-js.jsp" %>
  </body>
</html>
    