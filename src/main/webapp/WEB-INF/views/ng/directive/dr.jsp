<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="myApp">
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">-->
	
<script src="<%=basePath%>js/angulaerjs/angular-1.3.0.js"></script>
<script src="<%=basePath%>js/jquery/jquery-1.11.1.js"></script>


	<script type="text/javascript">

		var app = angular.module('myApp', []);
		
		app.directive('myDirective', function() {
			return {
				restrict : 'EA',
				repace : true,
				template : '<a href="http://google.com">go google</a>'
			};
		});

		app.run(function($rootScope, $timeout) {
			$rootScope.isDisabled = true;
			$timeout(function() {
				$rootScope.isDisabled = false;
			}, 5000);
		});
		//过滤器

		angular.module('myApp', []).filter('capitalize', function() {
			return function(input) { 
		
				if (input) { 
				return input[0].toUpperCase() + input.slice(1); 
				}
			};
		});
	</script>
  </head>
	<body >
		<my-directive></my-directive>
		<h2>布尔类型</h2>
		<h3>1. ng-disabled </h3>
	<input type="text" ng-model="someProperty" placeholder="TypetoEnable"> 
	<button ng-model="button" ng-disabled="!someProperty">提交</button> 
		<textarea ng-disabled="isDisabled">等待5秒</textarea>
		<h3>2. ng-readonly</h3>
		<h1 >过滤器</h1>
		<!-- Ginger loves dog treats --> 
		{{ 'ginger loves dog treats' | lowercase | capitalize }} 
		
	</body>
</html>
