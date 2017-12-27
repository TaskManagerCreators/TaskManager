var app = angular.module('delete' , []);
app.controller('deleteController' , function($scope , $http){
  $http.defaults.useXDomain = true;
  $scope.isError = false;
  $scope.deleteById = function(){
    $http.delete('http://127.0.0.1:8080/tasks?id='+$scope.id).
    success(function(){
      $scope.isError = false;
      $scope.msg = 'Deleting finished successfully';
    }).error(function(data){
      $scope.isError = true;
      $scope.msg = 'Deleting finished with some errors';
    });

  };
  $scope.deleteByName = function(){
    $http.delete('http://127.0.0.1:8080/tasks?name='+$scope.name).
    success(function(){
      $scope.isError = false;
      $scope.msg = 'Deleting finished successfully';
    }).error(function(data){
      $scope.isError = true;
      $scope.msg = 'Deleting finished with some errors';
    });


  };
  $scope.deleteAll = function() {
    $http.delete('http://127.0.0.1:8080/tasks').
    success(function(data){
      $scope.isError = false;
      $scope.msg = 'Deleting finished successfully';
    }).error(function(data){
      $scope.isError = true;
      $scope.msg = 'Deleting finished with some errors';
    });

  };

});
