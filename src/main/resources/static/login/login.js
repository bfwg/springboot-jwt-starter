angular.module('myApp.login', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location',
  function($scope, $rootScope, $http, $location) {
  $scope.error = false;
  $rootScope.selectedTab = $location.path() || '/';

  $scope.credentials = {};
  $scope.login = function() {
    // We are using formLogin in our backend, so here we need to serialize our form data
    $http({
      url: 'auth/login',
      method: 'POST',
      data: $scope.credentials,
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(function(res) {
      $rootScope.authenticated = true;
      $location.path("#/");
      $rootScope.selectedTab = "/";
      $scope.error = false;
    })
    .catch(function() {
      $rootScope.authenticated = false;
      $scope.error = true;
    });
  };
}]);
