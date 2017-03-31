angular.module('myApp.login', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location', '$httpParamSerializerJQLike',
  function($scope, $rootScope, $http, $location, $httpParamSerializerJQLike) {
  var self = this
  $rootScope.selectedTab = $location.path() || '/';

  $scope.credentials = {};
  $scope.login = function() {
    // We are using formLogin in our backend, so here we need to serialize our form data
    $http({
      url: 'login',
      method: 'POST',
      data: $httpParamSerializerJQLike($scope.credentials),
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(function(res) {
      $rootScope.authenticated = true;
      $location.path("#/");
      $rootScope.selectedTab = "/";
      self.error = false;
    })
    .catch(function() {
      $rootScope.authenticated = false;
      $location.path("/login");
      $rootScope.selectedTab = "/login";
      self.error = true;
    });
  };
}]);
