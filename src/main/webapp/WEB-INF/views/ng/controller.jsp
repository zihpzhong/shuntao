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
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">-->
	
<script src="<%=basePath%>js/angulaerjs/angular-1.3.0.js"></script>
<script src="<%=basePath%>js/jquery/jquery-1.11.1.js"></script>



<script type="text/javascript"> 
$().ready(function() { 
	//alert("1");
});
//holle world bigin
	var app=angular.module("myApp",[]);
	app.controller("myController",function($scope,$timeout,$parse) {
	
		$scope.name = "World1";
		//$scope.person="父类";
		$scope.person={
				name:'22122'
		};
		var updateClock = function() {
			$scope.clock = new Date();
			$timeout(function() {
				updateClock();
			}, 1000);
		};
		updateClock();
	});

	//控制器 c
	app.controller('FirstController', function($scope) {
		$scope.counter = 0;
		$scope.add = function(amount) {
			//alert(amount);
			$scope.counter += amount;
		};
		$scope.subtract = function(amount) {
			$scope.counter -= amount;
		};

	});
		/*表达式*/
	app.controller("parseController",function($scope,$parse) {
		$scope.$watch('expr',function(newVal,oldVal,scope){
		
			if(newVal!=oldVal){
				// 用该表达式设置parseFun 
				var parseFun = $parse(newVal); 
				// 获取经过解析后表达式的值 
				$scope.parseValue = parseFun(scope); 
			}
		});
	});
	app.controller("interController",function($scope,$interpolate){
		$scope.$watch('to',function(body){
			//alert(body);
			if(body){
				var template=$interpolate(body);
				$scope.previewText=template({to:$scope.to});
			}
		});
		
	});
	app.directive('myDirective',function(){
		return{
			restrict:'E',
			template:'<a href="http://google.com">go google</a>'
		};
	});
</script> 

</head>

<body ng-app ="myApp">
	<!-- holle world -->
	 <div ng-controller="myController">
		AngularJS hello world!
		<h1>Holle {{name}}</h1>
		
		<h1>{{person}}</h1>
		<h2>{{person.name}}</h2>
		
	</div>
	<h1>控制器  主要了解作用域"$scope" 和层级关系</h1>
	<div ng-controller="FirstController">
		<h4>add</h4>
		<button ng-click="add(1)" class="button">Add</button>
		<a ng-click="subtract(1)"  class="button alert">Subtract</a>
		<h4>Current count: {{ counter }}</h4>
	</div>
	<h1>表达式</h1>
	<h6>ng-model 用法</h6>
	<h6>placeholder 用法 文本提示</h6>
	<h6>$watch 用法:scope中监视其中的变量</h6>
	<h6>$parse 用法:$parse服务可以讲一个表达式转换为一个函数。这个函数可以被调用，其中的参数是一个上下文对象，通常来说是作用域。
	    exp:3+2
	</h6>
	
	<div  ng-controller="parseController">
	<input ng-model="expr" type="text" placeholder="E A XEPR">
		<h2>{{parseValue}}</h2>
	</div>
	 <h2>6.2  插值字符串 </h2>
	 <div ng-controller='interController'>
	 	<input ng-model='to'  placeholder="收件人" >
	 	<textarea ng-model="emailBody"></textarea> 
		<pre>{{previewText}}</pre> 
	 </div>
	 <h2>指令</h2>
	 	<my-directive></my-directive>
</body>
</html>
