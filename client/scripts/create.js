var app = angular.module('create' , []);
app.controller('createController' , function($scope , $http){
  $http.defaults.useXDomain = true;
  $scope.isError = false;
  $scope.create = function(name , describe , time , date , reactionType , reactionValue) {
    oldDate = date.split('-');
    newDate = new Array(3);
    newDate[0] = oldDate[2];
    newDate[1] = '.'+oldDate[1];
    newDate[2] = '.'+oldDate[0];
    date = newDate[0]+newDate[1]+newDate[2];
    var task = {
      name:name,
      describe:describe,
      targetTime:time+' '+date,
      completedTime: null,
      reaction:{value:reactionValue,type:reactionType},
      contacts:null,
      status:"SCHEDULED"
    };
    $http.post('http://127.0.0.1:8080/tasks' ,JSON.stringify(task)).
    success(function(){
      $scope.isError = false;
      $scope.msg = 'Creating finished without errors';
    }).error(function(){
      $scope.isError = true;
      $scope.msg = 'Creating finished with some errors';
    });
  };
});
