<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>YuanBenEc</title>
	<script type="text/javascript" src="<%=basePath%>js/jquery/jquery-1.11.1.js"></script>
	<!-- 引入STGrid相关JS，CSS -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery/plugins/stgrid/css/style.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery/plugins/stgrid/jquery.stgrid.js"></script>
	<!-- 引入jQuery扩展库 -->
	<script type="text/javascript" src="<%=basePath%>js/jquery/plugins/jquery.st.extend.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function () {
			//初始化Grid
			jQuery("#testTb").STGrid({
				url:'<%=basePath%>news/list/query',//查询URL
				idField : 'AREAID',//指定ID字段
				width: 800,//表格宽度
				pageSize : 10, //每页显示的数量
				maxPageSize : 500, //不分页时查询的最大数据条数
				goPageNum : 8, //分页条中快捷定位数字的数量,小于5则不显示
				showSN : true, //是否显示序号
				selectAble : true, //是否可选择表格
				singleSelect : false, //是否单选行
				columns:[[	
							{field:'AREAID',title:'区域ID',width:120,align:'center'},
							{field:'PARENTID',title:'父级ID',width:120,align:'center'},
							{field:'AREANAME',title:'区域名称',width:200,align:'left'}
						]]
			});
			//加载数据
			jQuery("#testTb").STGrid('load',{name:'caojian'});
		});
		
		//获取选择的行数据
		function getSel () {
			var t = jQuery("#testTb").STGrid('getSelections');
			alert(t);
		}
		
		//重新加载
		function reload () {
			jQuery("#testTb").STGrid('reload');
		}

		//上一页
		function goPrevPage () {
			jQuery("#testTb").STGrid('goPrevPage');
		}
		
		//下一页
		function goNextPage () {
			jQuery("#testTb").STGrid('goNextPage');
		}
		
		//加载指定页
		function loadPage (n) {
			jQuery("#testTb").STGrid('loadPage',n);
		}
		
		//改变宽度
		function resize (n) {
			jQuery("#testTb").STGrid('resize',{width:500});
		}		
		
	</script>
  </head>
  
  <body>
    YuanBenEc Version 1.0  
    <br /> <br /> 
    <a href="javascript:void(0);" onclick="javascript:getSel();">获取选择的行</a>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="javascript:reload();">重新加载</a>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="javascript:goPrevPage();">上一页</a>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="javascript:goNextPage();">下一页</a>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="javascript:loadPage(2);">指定页(第二页)</a>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="javascript:resize();">改变宽度</a>&nbsp;&nbsp;
    <br /> <br /> 
    
    <!-- STGrid -->
    <table id="testTb" cellpadding="0" cellspacing="0" border="0" class="tabn">
    	<thead></thead>
    	<tbody></tbody>
    	<tfoot></tfoot>
    </table>
    <div id="testTb-pageBar" style="display: none; margin-top:16px;"></div>
    <!-- STGrid -->
    
    
  </body>
</html>
