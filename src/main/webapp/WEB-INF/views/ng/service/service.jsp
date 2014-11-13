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
	
		var myApp = angular.module("myApp", []);
		myApp.factory('UserService', function($http) {
			var current_user;
			return {
				getCurrentUser : function() {
					return current_user;
				},
				setCurrentUser : function(user) {
					return current_user = user;
				}
			}
		})

		angular.module('myApp.services', []).factory('githubService',
				function($http) {
					var githubUrl = 'https://api.github.com';
					var runUserRequest=function(username,path){
						return $http({
							method:'JSON',
							url:githubUrl+'/users/'+username+path+'?callback=JSON_CALLBACK'
						});
					}
					//var serviceInstance = {};
					// 我们的第一个服务 
					//alert(serviceInstance);
					//return serviceInstance;
					return {events:function(username){
						return runUserRequest(username, 'events');
					}}
				});
	
		angular.module('myApp', [ 'myApp.services' ]).controller(
				'ServiceController', function($scope, githubService) {
					// 我们可以调用对象的事件函数 
					$scope.$watch('username',function(newUsername){
						//$scope.events = 
						githubService.events(newUsername).success(function(data,status,headers){
							$scope.events = data.data;
						})
					})
					
				});
		//factory 服务
		angular.module("myApp")
			.factory('myService',function(){
				return{
					'username':'auser'
				}
			})
			.provider('myService',{
				$get:function(){
					return{
						username:'auser'
					}
				}
			})
		//service  服务  参数:name,constructor(函数名)
		var Person=function($http){
			this.getName=function(){
				return $http({method:'GET',url:jsBasePath+'ng/getsv'})
			}
		}
		angular.module("myApp").service('personService',Person);
	</script>
  </head>
  
  <body ng-app="myApp">
	This is my JSP page.
	<br>
	<div ng-controller="ServiceController">
		<label for="username"> Type in a GitHub username </label> <input
			type="text" ng-model="username" placeholder="Enter a GitHub username" />
		<ul>
			<li ng-repeat="event in events">
				<!-- 
				event.actor and event.repo are returned 
				by the github API. To view the raw 
				API, uncomment the next line: 
				--> <!-- {{ event | json }} -->
			{{ event.actor.login }} {{event.repo.name }}
			</li>
		</ul>
	</div>


</body>
</html>
