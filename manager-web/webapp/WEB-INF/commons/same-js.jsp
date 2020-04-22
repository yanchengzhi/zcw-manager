<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
  <script type="text/javascript">
  changePageStyle("${currentUrl}");
  function changePageStyle(url){
    //为当前页面的链接添加样式
    $('a[href="${APP_PATH}/'+url+'"]').css('color','red');
    //将父菜单展开
    $('a[href="${APP_PATH}/'+url+'"]').parents('.list-group-item').removeClass('tree-closed');
    //parent方法是获取直接父元素
    //parents方法是获取所有父元素
    $('a[href="${APP_PATH}/'+url+'"]').parent().parent('ul').show(100); 
  }
 </script>



