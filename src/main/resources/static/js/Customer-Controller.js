'use strict';
var app = angular.module('app.controllers', []);


app.controller('CustomerListCtrl', ['$scope', 'CustomersFactory', 'CustomerFactory', '$location',
                                    function ($scope, CustomersFactory, CustomerFactory, $location) {


	$scope.editCustomer = function (customerId) {
		$location.path('/customer-edit/' + customerId);
	};


	$scope.deleteCustomer = function (customerId) {
		CustomerFactory.delete({ id: customerId }).$promise.then(function(){
			$scope.customers = CustomersFactory.query();
		});
	};


	$scope.createNewCustomer = function () {
		$location.path('/customer-add');
	};

	$scope.customers = CustomersFactory.query();

}]);

app.controller('CustomerEditCtrl', ['$scope', '$routeParams', 'CustomerFactory', '$location',
                                    function ($scope, $routeParams, CustomerFactory, $location) {


	$scope.updateCustomer = function () {
		CustomerFactory.update($scope.customer).$promise.then(function(){
			$location.path('/customer-list');
		});


	};


	$scope.cancel = function () {
		$location.path('/customer-list');
	};

	$scope.customer = CustomerFactory.show({id: $routeParams.id});
}]);

app.controller('CustomerAddCtrl', ['$scope', 'CustomersFactory', '$location',

                                   function ($scope, CustomersFactory, $location) {


	$scope.createNewCustomer = function () {
		CustomersFactory.create($scope.customer).$promise.then(function(){
			$location.path('/customer-list');
		});
	};
}]);