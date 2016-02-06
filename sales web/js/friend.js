var app = angular.module("friendApp", []);
app.controller("friendCtrl", function($scope) {
  
  //friends array
  $scope.friends = [
    {name:'Pan Dan'}, 
    {name:'Gu Yuqing'}
  ];
  
  //add friend button
  $scope.addFriend = function() {
    $scope.friends.push(
      {name:$scope.nameInput}
    );
    $scope.nameInput = "";
    $scope.nameDesc = "";
  }
  
  //remove freind button
  $scope.removeFriend = function(index) {
    $scope.friends.splice(index, 1);
  }

  
});