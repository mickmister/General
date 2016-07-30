var app = angular.module('JamApp', ['ngRoute', 'ngStorage']);
console.log('hello there');
app.config(function($routeProvider, $locationProvider)
{
    $routeProvider
        .when('/home', {
            templateUrl: 'app/login/login-view.html',
            controller: 'LandingController'
        })
        .otherwise({
            redirectTo: '/home'
        });

        $locationProvider.html5Mode(true);
});

app.config(['$httpProvider', function($httpProvider) 
{
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
  }
]);



/*
app.factory('httpRequestInterceptor', ['Session', function (Session) 
{
  return {
    request: function (config) 
    {
        var session = Session.get();
        return config;
    }
  };
}]);
*/

/*
app.config(function ($httpProvider)
{
  $httpProvider.interceptors.push('httpRequestInterceptor');
});
*/

