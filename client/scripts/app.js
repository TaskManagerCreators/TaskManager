var app = angular.module('main' , []);

  app.controller('mainController' , function($scope , $http){
    $scope.xa=1;
     $http.defaults.useXDomain = true;
     $scope.isError = false;
     $scope.getByName = function(){
    $http.get('http://127.0.0.1:8080/tasks?name='+$scope.name).
    success(function(data , status){
      $scope.tasks=data;
      $scope.status=status;
      $scope.isError = false;
    }).error(function(data , status){
      $scope.isError = true;
    });
  };
  $scope.getAll = function(){
    $http.get('http://127.0.0.1:8080/tasks').
    success(function(data , status){
      $scope.tasks = data;
      $scope.isError = false;
    }).error(function(data , status){
      $scope.isError = true;
    });
  };
  $scope.getById = function(){
    $http.get('http://127.0.0.1:8080/tasks/'+$scope.ids).
    success(function(data , status){
      $scope.tasks = data;
      $scope.isError = false;
    }).error(function(data , status){
      $scope.isError = true;
    });
  };
});
