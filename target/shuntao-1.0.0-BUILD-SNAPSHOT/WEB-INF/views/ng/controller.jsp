<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=basePath%>/js/angulaerjs/angular-1.3.0.js"></script>
<script type="text/javascript"> 
//holle world bigin
	var app=angular.module("myApp",[]);
	app.controller("myController",function($scope,$timeout) {
		$scope.name = "World1";
		
		var updateClock = function() {
			$scope.clock = new Date();
			$timeout(function() {
				updateClock();
			}, 1000);
		};
		updateClock();
	});
//控制器 c
</script> 

</head>

<body ng-app ="myApp">
	<!-- holle world -->
	 <div ng-controller="myController">
		AngularJS hello world!
		<h1>Holle {{clock}} {{name}}</h1>
	</div>
	
</body>
</html>
