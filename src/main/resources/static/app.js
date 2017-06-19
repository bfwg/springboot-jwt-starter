'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myApp.dashboard',
  'myApp.login',
  'myApp.services'
]).
config(['$locationProvider', '$routeProvider', "$httpProvider", function($locationProvider, $routeProvider, $httpProvider) {

	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  $routeProvider.otherwise({redirectTo: '/'});
}])
.controller('NavigationCtrl', ['$scope', '$rootScope', '$http', '$location',
  function($scope, $rootScope, $http, $location) {
  var self = this

  $rootScope.selectedTab = $location.path() || '/';

  $scope.logout = function() {
    $http.post('auth/logout', {}).finally(function() {
      $rootScope.authenticated = false;
      $location.path("#/");
      $rootScope.selectedTab = "/";
    });
  }

  $scope.setSelectedTab = function(tab) {
    $rootScope.selectedTab = tab;
  }

  $scope.tabClass = function(tab) {
    if ($rootScope.selectedTab == tab) {
      return "active";
    } else {
      return "";
    }
  }

  if ($rootScope.authenticated) {
    $location.path('/');
    $rootScope.selectedTab = '/';
    return;
  }
}]);