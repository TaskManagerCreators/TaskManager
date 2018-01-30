var app = angular.module('main' , []);

  app.controller('mainController' , function($scope , $http){
    $scope.xa=1;
     $http.defaults.useXDomain = true;
     $scope.isError = false;
      $scope.nextPage = 1;
     $scope.getByName = function(){
     if( $scope.name == '' || $scope.name == null) {
        $scope.getAll();
     }
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

    $http.get('http://127.0.0.1:8080/tasks?next='+$scope.nextPage).
    success(function(data , status){
      $scope.tasks = data;
      $scope.isError = false;
      $scope.size = parseInt(data[0].size/20) + 1;
      $scope.pages = init($scope.size);
        for(i = 0 ; i < 24 ; i++){
            if(i<10)
                $scope.hours[i] = '0'+i+':00';
            else
                $scope.hours[i] = i+':00';

        }
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
  $scope.deleteById = function (task) {
      $http.delete('http://127.0.0.1:8080/tasks?id='+task.id).
      success(function(){
          $scope.isError = false;
          $scope.getAll();
      }).error(function(data){
          $scope.isError = true;
      });
  };
  function init(size){
      var result = new Array(size);
      for(i = 0;i<size;i++){
          result[i] = i+1;
      }
      return result;
  }
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
              targetTime:date+' '+time,
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
