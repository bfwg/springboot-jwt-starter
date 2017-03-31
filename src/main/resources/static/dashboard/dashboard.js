'use strict';

angular.module('myApp.dashboard', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: 'dashboard/dashboard.html',
    controller: 'DashboardCtrl'
  });
}])

.controller('DashboardCtrl', ['$scope', '$rootScope', '$http', 'AuthService',
  function($scope, $rootScope, $http, authService) {
    $scope.serverResponse = '';
    $scope.responseBoxClass = '';

    var setResponse = function(res, success) {
      if (success) {
        $scope.responseBoxClass = 'alert-success';
      } else {
        $scope.responseBoxClass = 'alert-danger';
      }
      $scope.serverResponse = res;
      $scope.serverResponse.data = JSON.stringify(res.data, null, 2);
    }

    if ($rootScope.authenticated) {
      authService.getUser()
      .then(function(response) {
        $scope.user = response.data;
      });
    }

    $scope.getUserInfo = function() {
      $http.get('user')
      .then(function(response) {
        setResponse(response, true);
      })
      .catch(function(response) {
        setResponse(response, false);
      });
    }

    $scope.getAllUserInfo = function() {
      $http.get('user/all')
      .then(function(response) {
        setResponse(response, true);
      })
      .catch(function(response) {
        setResponse(response, false);
      });
    }
  }]);
