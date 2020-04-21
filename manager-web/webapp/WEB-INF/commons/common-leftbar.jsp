<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<ul style="padding-left:0px;" class="list-group">
					<li class="list-group-item tree-closed" >
						<a href="${APP_PATH}/main.html"><i class="glyphicon glyphicon-dashboard"></i> 控制面板</a> 
					</li>
					<!-- 遍历菜单 -->
					<c:forEach items="${userMenus}" var="pMenu">
					   <li class="list-group-item tree-closed">
					      <span><i class="${pMenu.icon}"></i>${pMenu.name}<span class="badge" style="float:right">${pMenu.childs.size()}</span></span>
					      <ul style="margin-top:10px;display:none;">
					         <c:forEach items="${pMenu.childs}" var="cMenu">
					           <li style="height:30px;">
								  <a href="${APP_PATH}/${cMenu.url}"><i class="${cMenu.icon}"></i> ${cMenu.name}</a> 
							   </li>
					         </c:forEach>
					      </ul>
					   </li>
					</c:forEach>
				</ul>
			</div>
        </div>