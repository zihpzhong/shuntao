<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'service.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
	<script type="text/javascript">var jsBasePath = '<%=basePath%>';</script>
	<script src="<%=basePath%>js/angulaerjs/angular-1.3.0.js"></script>
	<script src="<%=basePath%>js/jquery/jquery-1.11.1.js"></script>
	<script type="text/javascript">
		var myApp = angular.module("myApp", [])
		myApp.controller('myController', function($scope, $http) {
			$scope.$watch('name', function() {
				$http({method:'POST',url:jsBasePath+'ng/xhr',
					data : {field : '1',value : 2}
				}).success(function(data,status,headers,config) {
					//c.$setValidity('unique', data.isUnique);
					//alert(data.msg)
					//jQuery("#resmsg").html(data.msg);
				}).error(function(data) {
					//c.$setValidity('unique', false);
				});
			})
		})
	</script>
  </head>
  
  <body ng-app="myApp">
    This is my JSP page. <br>
    <div ng-controller="myController">
    	<input name="name" ng-model="name">
    </div>
  </body>
</html>
