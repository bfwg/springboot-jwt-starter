angular.module('hello', [ 'ngRoute', 'authModule' ])
  .config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
      templateUrl : 'home.html',
      controller : 'home',
      controllerAs: 'controller'
    }).when('/login', {
      templateUrl : 'login.html',
      controller : 'navigation',
      controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  })

.controller('home', function($rootScope, $http, authentication) {
  var self = this;
  $rootScope.authenticated = authentication.isAuthenticated();
  if ($rootScope.authenticated) {
    authentication.getUser()
    .then(function(response) {
      self.user = response.data;
    });
  }

  // $http.get('/resource/').then(function(response) {
    // self.greeting = response.data;
  // });
})

.controller('navigation', function($scope, $rootScope, $http, $location, $httpParamSerializerJQLike, authentication) {

  var self = this
  self.selectedTab = $location.path();

  console.log(self.selectedTab);

  self.credentials = {};
  self.login = function() {
    // We are using formLogin in our backend, so here we need to serialize our form data
    $http({
      url: 'login',
      method: 'POST',
      data: $httpParamSerializerJQLike(self.credentials),
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(function(res) {
      $scope.apply(function() {
        $rootScope.authenticated = true;
        $location.path("/");
        self.selectedTab = "/";
        self.error = false;
      });
    })
    .catch(function() {
      $scope.apply(function() {
        $rootScope.authenticated = false;
        $location.path("/login");
        self.selectedTab = "/login";
        self.error = true;
      });
    });
  };


  self.logout = function() {
    $http.post('logout', {}).finally(function() {
      $rootScope.authenticated = false;
      $location.path("#/");
      self.selectedTab = "/";
    });
  }

  self.setSelectedTab = function(tab) {
    self.selectedTab = tab;
  }

  self.tabClass = function(tab) {
    if (self.selectedTab == tab) {
      return "active";
    } else {
      return "";
    }
  }

});

angular.module('authModule', [ 'ngCookies' ])
.factory('authentication', function($http, $cookies) {
  return {
    isAuthenticated: function() {
      return !!$cookies.get('c_user');
    },
    getUser: function() {
      return $http.get('user')
    }
  };
});
