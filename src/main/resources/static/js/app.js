angular.module('hello', [ 'ngRoute', 'authModule' ])
  .config(function($routeProvider, $httpProvider, $locationProvider) {

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
  self.serverResponse = '';
  self.responseBoxClass = '';
  $rootScope.authenticated = authentication.isAuthenticated();
  if ($rootScope.authenticated) {
    authentication.getUser()
    .then(function(response) {
      self.user = response.data;
    });
  }

  self.getUserInfo = function() {
		$http.get('user')
		.then(function(response) {
			setResponse(response, true);
		})
		.catch(function(response) {
			setResponse(response, false);
		});
  }

  self.getAllUserInfo = function() {
		$http.get('user/all')
		.then(function(response) {
			setResponse(response, true);
		})
		.catch(function(response) {
			setResponse(response, false);
		});
  }

  setResponse = function(res, success) {
    if (success) {
			self.responseBoxClass = 'alert-success';
    } else {
			self.responseBoxClass = 'alert-danger';
    }
		self.serverResponse = res;
		self.serverResponse.data = JSON.stringify(res.data, null, 2);
  }

  // $http.get('/resource/').then(function(response) {
    // self.greeting = response.data;
  // });
})

.controller('navigation', function($rootScope, $http, $location, $httpParamSerializerJQLike, authentication) {
  var self = this
  $rootScope.authenticated = authentication.isAuthenticated();
  $rootScope.selectedTab = $location.path() || '/';

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

  self.logout = function() {
    $http.post('logout', {}).finally(function() {
      $rootScope.authenticated = false;
      $location.path("#/");
      $rootScope.selectedTab = "/";
    });
  }

  self.setSelectedTab = function(tab) {
    $rootScope.selectedTab = tab;
  }

  self.tabClass = function(tab) {
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
