angular.module('app', ['app.services', 'app.controllers','ngRoute','ngMessages'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        .when('/customer-list',
        		{templateUrl: 'list.html',
        		controller: 'CustomerListCtrl'})
        .when('/customer-edit/:id',
        		{templateUrl: 'edit.html', 
        		controller: 'CustomerEditCtrl'})
        .when('/customer-add',
        		{templateUrl: 'add.html',
        		controller: 'CustomerAddCtrl'})
        .otherwise({redirectTo: '/customer-list'});
    }]);