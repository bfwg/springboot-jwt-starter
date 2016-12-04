angular.module('hello', [])
  .controller('home', function($scope) {
    $scope.greeting = {id: 'xxx', content: 'Hello World!'}
})
