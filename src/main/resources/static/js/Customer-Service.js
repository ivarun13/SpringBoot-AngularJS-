var services = angular.module('app.services', ['ngResource']);

services.factory('CustomersFactory', function ($resource) {
    return $resource('http://localhost:8888/customers', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

services.factory('CustomerFactory', function ($resource) {
    return $resource('http://localhost:8888/customers/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE'}
    })
});
