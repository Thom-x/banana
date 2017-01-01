'use strict';

angular.module('sprintGraphApp', ['ngResource','ui.bootstrap', 'ui.router']);

angular.module('sprintGraphApp')
    .constant("BASE_URL", "localhost:9000/api");

angular.module('sprintGraphApp').config(function ($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise("/graph");

  $stateProvider
    .state('graph', {
      url: "/graph",
      templateUrl: "app/views/graph.html",
      controller: 'GraphCtrl',
      controllerAs: 'graphCtrl'
    })
    .state('admin', {
      url: "/admin",
      templateUrl: "app/views/admin.html",
      controller: 'AdminCtrl',
      controllerAs: 'adminCtrl'
    });
   

});
